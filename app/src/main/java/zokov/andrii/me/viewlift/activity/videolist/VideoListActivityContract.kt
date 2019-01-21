package zokov.andrii.me.viewlift.activity.videolist

import android.support.annotation.UiThread
import zokov.andrii.me.viewlift.activity.IBasePresenter
import zokov.andrii.me.viewlift.activity.IBaseActivity
import zokov.andrii.me.viewlift.model.VideoItemWrapper

interface VideoListActivityContract {
    interface View : IBaseActivity {
        @UiThread
        fun showVideoItems(videoItems: List<VideoItemWrapper>?)
        @UiThread
        fun showVideo(videoItemWrapper: VideoItemWrapper?)
        @UiThread
        fun showNoVideoItemsFound(show: Boolean)
        @UiThread
        fun showServerError()
        @UiThread
        fun showVideoIsNotAvailable()
        @UiThread
        fun defaultVideoPlayerNotAvailable()
    }

    interface Presenter : IBasePresenter {
        fun itemSelected(id: Long)
        fun hasNoDefaultMediaPlayer()
    }
}