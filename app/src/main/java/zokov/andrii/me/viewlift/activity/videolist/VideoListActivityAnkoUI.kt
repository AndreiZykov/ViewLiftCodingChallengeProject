package zokov.andrii.me.viewlift.activity.videolist

import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.leochuan.ScaleLayoutManager
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import zokov.andrii.me.viewlift.R
import zokov.andrii.me.viewlift.adapter.videolist.VideoListAdapter

class VideoListActivityAnkoUI(private val videoListAdapter: VideoListAdapter) : AnkoComponent<VideoListActivity> {
    companion object {
        private const val NO_VIDEO_FOUND_LAYOUT_WIDTH = 120
        private const val NO_VIDEO_FOUND_LAYOUT_HEIGHT = 140
        private const val NO_VIDEO_FOUND_ICON_WIDTH_HEIGHT = 72
        private const val NO_VIDEO_FOUND_DESCRIPTION_BOTTOM_MARGIN = 32
        private const val SCALE_LAYOUT_MANAGER_MIN_SCALE = 0.9F
        private const val SCALE_LAYOUT_MANAGER_ITEM_SPACING = 0
        private const val PROGRESS_BAR_WIDTH_HEIGHT = 72
    }

    private lateinit var noVideosFoundLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    override fun createView(ui: AnkoContext<VideoListActivity>) = ui.apply {
        relativeLayout {
            backgroundResource = R.color.video_list_screen_background
            recyclerView {
                layoutManager = ScaleLayoutManager.Builder(ctx, SCALE_LAYOUT_MANAGER_ITEM_SPACING)
                    .setMinScale(SCALE_LAYOUT_MANAGER_MIN_SCALE).build()
                adapter = videoListAdapter
            }.lparams(matchParent, matchParent)
            progressBar = progressBar { visibility = View.INVISIBLE }
                .lparams(dip(PROGRESS_BAR_WIDTH_HEIGHT), dip(PROGRESS_BAR_WIDTH_HEIGHT)) { centerInParent() }
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

    internal fun showNoVideosFoundView(show: Boolean) {
        noVideosFoundLayout.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    internal fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

}