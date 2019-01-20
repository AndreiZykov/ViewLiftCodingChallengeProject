package zokov.andrii.me.viewlift.activity.videolist

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import zokov.andrii.me.viewlift.R
import zokov.andrii.me.viewlift.adapter.videolist.VideoListAdapter

class LoginActivityAnkoUI(
    private val videoListAdapter: VideoListAdapter,
    private val refreshConsumer: Consumer<Boolean>
) : AnkoComponent<VideoListActivity> {
    companion object {
        private const val NO_VIDEO_FOUND_LAYOUT_WIDTH = 120
        private const val NO_VIDEO_FOUND_LAYOUT_HEIGHT = 140
        private const val NO_VIDEO_FOUND_ICON_WIDTH_HEIGHT = 72
        private const val NO_VIDEO_FOUND_DESCRIPTION_BOTTOM_MARGIN = 32
    }
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var noVideosFoundLayout: RelativeLayout
    override fun createView(ui: AnkoContext<VideoListActivity>) = ui.apply {
        relativeLayout {
            swipeRefreshLayout = swipeRefreshLayout {
                setOnRefreshListener { Observable.just(true).subscribe(refreshConsumer) }
                recyclerView {
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = videoListAdapter
                }
            }.lparams(matchParent, matchParent)

            noVideosFoundLayout = relativeLayout {
                visibility = View.INVISIBLE
                imageView { imageResource = R.drawable.ic_sad_face }
                    .lparams(dip(NO_VIDEO_FOUND_ICON_WIDTH_HEIGHT), dip(NO_VIDEO_FOUND_ICON_WIDTH_HEIGHT)) {
                        centerHorizontally()
                        alignParentTop()
                    }
                textView(R.string.no_videos_found)
                    .lparams {
                        centerHorizontally()
                        alignParentBottom()
                        bottomMargin = dip(NO_VIDEO_FOUND_DESCRIPTION_BOTTOM_MARGIN)
                    }
            }.lparams(dip(NO_VIDEO_FOUND_LAYOUT_WIDTH), dip(NO_VIDEO_FOUND_LAYOUT_HEIGHT)) { centerInParent() }
        }
    }.view

    internal fun hideRefreshProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    internal fun showNoVideosFoundView(show: Boolean) {
        noVideosFoundLayout.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

}