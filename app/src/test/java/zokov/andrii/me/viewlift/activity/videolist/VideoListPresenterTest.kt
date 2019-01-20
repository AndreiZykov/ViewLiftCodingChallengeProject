package zokov.andrii.me.viewlift.activity.videolist

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import zokov.andrii.me.viewlift.RxImmediateSchedulerRule
import zokov.andrii.me.viewlift.data.ApplicationRemoteDataSource
import zokov.andrii.me.viewlift.model.VideoItemWrapper
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class VideoListPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        internal var schedulers = RxImmediateSchedulerRule()
        private const val NON_EMPTY_STRING = "NON_EMPTY_STRING"
        private const val EMPTY_STRING = ""
        private const val DURATION = 0.0
    }

    @Mock
    lateinit var view: VideoListActivityContract.View

    @Mock
    lateinit var applicationRemoteDataSource: ApplicationRemoteDataSource

    @InjectMocks
    lateinit var videoListPresenter: VideoListPresenter

    @Captor
    lateinit var videoItemCaptor: ArgumentCaptor<VideoItemWrapper>

    @Test
    fun subscribe_noVideoListItemsStoredYet_fetchContentItemsShouldBeCalled() {
        // before
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(ArrayList()))
        // when
        videoListPresenter.subscribe()
        // then
        verify(applicationRemoteDataSource, times(1)).fetchContentItems()
    }

    @Test
    fun subscribe_hasVideoItemsStored_fetchContentItemsShouldNotBeCalled() {
        // before
        videoListPresenter.videoItems.add(VideoItemWrapper(
            NON_EMPTY_STRING, NON_EMPTY_STRING,
            DURATION, NON_EMPTY_STRING
        ).apply { id = 1L })
        // when
        videoListPresenter.subscribe()
        // then
        verifyNoMoreInteractions(applicationRemoteDataSource)
    }

    @Test
    fun refreshVideoItems_fetchContentItemsShouldBeCalled() {
        // before
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(ArrayList()))
        // when
        videoListPresenter.refreshVideoItems()
        // then
        verify(applicationRemoteDataSource, times(1)).fetchContentItems()
    }

    @Test
    fun fetchContentItems_returnEmptyList_showNoVideoItemsFoundTrueOnViewShouldBeCalled() {
        // before
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(ArrayList()))
        // when
        videoListPresenter.fetchContentItems()
        // then
        verify(view, times(1)).showNoVideoItemsFound(true)
    }

    @Test
    fun fetchContentItems_returnNonEmptyList_showNoVideoItemsFoundFalseOnViewShouldBeCalled() {
        // before
        val videoItems = ArrayList<VideoItemWrapper>()
        repeat(5) {
            videoItems.add(VideoItemWrapper(
                NON_EMPTY_STRING, NON_EMPTY_STRING,
                DURATION, NON_EMPTY_STRING
            ).apply { id = it.toLong() })
        }
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(videoItems))
        // when
        videoListPresenter.fetchContentItems()
        // then
        verify(view, times(1)).showNoVideoItemsFound(false)
    }

    @Test
    fun fetchContentItems_returnNonEmptyList_everyItemStoredInPresenterVideoItemListShouldHaveNonNullUniqueId() {
        // before
        val videoItems = ArrayList<VideoItemWrapper>()
        repeat(5) {
            videoItems.add(VideoItemWrapper(
                NON_EMPTY_STRING, NON_EMPTY_STRING,
                DURATION, NON_EMPTY_STRING
            ).apply { id = it.toLong() })
        }
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(videoItems))
        // when
        videoListPresenter.fetchContentItems()
        // then
        assertThat(
            videoItems.size,
            `is`(videoListPresenter.videoItems.filter { it.id != null }.distinctBy { it.id }.size)
        )
    }

    @Test
    fun fetchContentItems_shouldHideRefreshProgress() {
        // before
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.just(ArrayList()))
        // when
        videoListPresenter.fetchContentItems()
        // then
        verify(view, times(1)).hideRefreshProgress()
    }

    @Test
    fun fetchContentItems_returnException_showServerErrorOnViewShouldBeCalled() {
        // before
        whenever(applicationRemoteDataSource.fetchContentItems())
            .thenReturn(Single.error(Exception(NON_EMPTY_STRING)))
        // when
        videoListPresenter.fetchContentItems()
        // then
        verify(view, times(1)).showServerError()
    }

    @Test
    fun itemSelected_5VideoItemsStored_itemIdShouldMatch() {
        // before
        repeat(5) {
            videoListPresenter.videoItems.add(VideoItemWrapper(
                NON_EMPTY_STRING, NON_EMPTY_STRING,
                DURATION, NON_EMPTY_STRING
            ).apply { id = it.toLong() })
        }
        // when
        videoListPresenter.itemSelected(videoListPresenter.videoItems.first().id!!)
        // then
        verify(view, times(1)).showVideo(videoItemCaptor.capture())
        assertThat(videoItemCaptor.value.id, `is`(videoListPresenter.videoItems.first().id))
    }

    @Test
    fun itemSelected_5VideoItemsStoredNoVideoUrls_shouldShowNoVideoAvailableErrorOnView() {
        // before
        repeat(5) {
            videoListPresenter.videoItems.add(VideoItemWrapper(
                NON_EMPTY_STRING, NON_EMPTY_STRING,
                DURATION, EMPTY_STRING
            ).apply { id = it.toLong() })
        }
        // when
        videoListPresenter.itemSelected(videoListPresenter.videoItems.first().id!!)
        // then
        verify(view, times(1)).showVideoIsNotAvailable()
    }

    @Test
    fun itemSelected_5VideoItemsStoredIntentionallySelectNonExistingItem_noInteractions() {
        // before
        repeat(5) {
            videoListPresenter.videoItems.add(VideoItemWrapper(
                NON_EMPTY_STRING, NON_EMPTY_STRING,
                DURATION, NON_EMPTY_STRING
            ).apply { id = it.toLong() })
        }
        // when
        videoListPresenter.itemSelected(videoListPresenter.videoItems.size.toLong())
        // then
        verifyNoMoreInteractions(view)
    }

    @Test
    fun unsubscribe_compositeDisposableShouldBeCleared() {
        // before
        Observable.interval(100, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .subscribe().apply { videoListPresenter.compositeDisposable.add(this) }
        // when
        videoListPresenter.unsubscribe()
        // then
        assertThat(videoListPresenter.compositeDisposable.size(), `is`(0))
    }

    @Test
    fun hasNoDefaultMediaPlayer_shouldShowDefaultVideoPlayerNotAvailableErrorMessage(){
        // when
        videoListPresenter.hasNoDefaultMediaPlayer()
        // then
        verify(view, times(1)).defaultVideoPlayerNotAvailable()
    }

}