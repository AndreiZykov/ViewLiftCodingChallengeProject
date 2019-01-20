package zokov.andrii.me.viewlift.activity.videolist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dagger.android.AndroidInjection
import io.reactivex.functions.Consumer
import org.jetbrains.anko.setContentView
import zokov.andrii.me.viewlift.R
import zokov.andrii.me.viewlift.adapter.videolist.VideoListAdapter
import zokov.andrii.me.viewlift.model.VideoItemWrapper
import javax.inject.Inject


class VideoListActivity : AppCompatActivity(), VideoListActivityContract.View {

    @Inject
    internal lateinit var presenter: VideoListActivityContract.Presenter

    @Inject
    internal lateinit var videoListAdapter: VideoListAdapter

    private lateinit var ui: LoginActivityAnkoUI

    override fun onCreate(args: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(args)
        videoListAdapter.itemClickConsumer = Consumer { presenter.itemSelected(it) }
        ui = LoginActivityAnkoUI(videoListAdapter, Consumer { presenter.refreshVideoItems() })
            .apply { setContentView(this@VideoListActivity) }
    }

    @UiThread
    override fun showVideo(videoItemWrapper: VideoItemWrapper?) {
        videoItemWrapper?.let {
            val i = Intent().apply {
                action = Intent.ACTION_VIEW
                setDataAndType(Uri.parse(it.videoUrl), "video/*")
            }
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            } else {
                presenter.hasNoDefaultMediaPlayer()
            }
        }
    }

    @UiThread
    override fun defaultVideoPlayerNotAvailable() {
        Toast.makeText(this, R.string.default_video_player_not_available, Toast.LENGTH_SHORT).show()
    }

    @UiThread
    override fun showVideoItems(videoItems: List<VideoItemWrapper>?) {
        videoItems?.let {
            videoListAdapter.apply {
                items.clear()
                items.addAll(it.toMutableList())
                notifyDataSetChanged()
            }
        }
    }

    @UiThread
    override fun showVideoIsNotAvailable() {
        Toast.makeText(this, R.string.video_is_not_available, Toast.LENGTH_SHORT).show()
    }

    @UiThread
    override fun showServerError() {
        Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show()
    }

    @UiThread
    override fun hideRefreshProgress() {
        ui.hideRefreshProgress()
    }

    @UiThread
    override fun showNoVideoItemsFound(show: Boolean) {
        ui.showNoVideosFoundView(show)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        presenter.unsubscribe()
        super.onPause()
    }
}
