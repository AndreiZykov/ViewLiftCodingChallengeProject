package zokov.andrii.me.viewlift.activity.videolist

import android.support.annotation.VisibleForTesting
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import zokov.andrii.me.viewlift.data.ApplicationRemoteDataSource
import zokov.andrii.me.viewlift.model.VideoItemWrapper
import javax.inject.Inject

/**
 * presentation for { @link VideoListActivity }
 * @see VideoListActivity
 */

class VideoListPresenter @Inject constructor(
    private val view: VideoListActivityContract.View,
    private val applicationRemoteDataSource: ApplicationRemoteDataSource
) : VideoListActivityContract.Presenter {

    @VisibleForTesting
    val compositeDisposable = CompositeDisposable()

    @VisibleForTesting
    val videoItems = ArrayList<VideoItemWrapper>()

    private var fetchContentItemsDisposable: Disposable? = null

    override fun subscribe() {
        if (videoItems.isEmpty())
            fetchContentItems()
    }

    override fun itemSelected(id: Long) {
        Observable.fromCallable { videoItems.find { it.id == id } }
            .subscribeOn(Schedulers.single()).observeOn(Schedulers.computation())
            .subscribe({ item ->
                // result can not be null by logic
                // so we ignore null situation and only safe unwrap optional
                item?.let { nonNullItem ->
                    if (nonNullItem.videoUrl.isEmpty()) {
                        view.showVideoIsNotAvailable()
                    } else {
                        view.showVideo(nonNullItem)
                    }
                }
            }) { /* never gonna happened */ }.apply { compositeDisposable.add(this) }
    }

    override fun hasNoDefaultMediaPlayer() = view.defaultVideoPlayerNotAvailable()

    /**
     * fetching and indexing all available content items
     * show "no available videos" if result is empty
     */

    @VisibleForTesting
    fun fetchContentItems() {
        fetchContentItemsDisposable?.takeUnless { it.isDisposed }?.dispose()
        fetchContentItemsDisposable = applicationRemoteDataSource.fetchContentItems()
            .flatMap { items ->
                return@flatMap Single.fromCallable {
                    var id = 0L; items.forEach { item -> item.id = id++ }
                    return@fromCallable videoItems.apply {
                        clear()
                        addAll(items)
                    }
                }
            }
            .subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgressBar(true) }
            .doFinally {
                view.showNoVideoItemsFound(videoItems.isEmpty())
                view.showProgressBar(false)
            }
            .subscribe({ view.showVideoItems(videoItems) }) { view.showServerError() }
            .apply { compositeDisposable.add(this) }
    }


    override fun unsubscribe() = compositeDisposable.clear()

}