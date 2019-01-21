package zokov.andrii.me.viewlift.adapter.videolist

import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import zokov.andrii.me.viewlift.R

class VideoListItemAnkoUI : AnkoComponent<ViewGroup> {
    companion object {
        private const val ROOT_LAYOUT_HORIZONTAL_PADDING = 6
        private const val THUMBNAIL_ICON_HEIGHT = 216
        private const val INFO_CONTAINER_TOP_MARGIN = 12
        private const val INFO_CONTAINER_BOTTOM_MARGIN = 8
        private const val TITLE_TEXT_SIZE = 19F
        private const val DURATION_TOP_MARGIN = 6
    }

    internal lateinit var thumbnailImageView: ImageView
    internal lateinit var titleValue: TextView
    internal lateinit var durationValue: TextView
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        relativeLayout {
            backgroundResource = R.color.transparent
            lparams(matchParent, wrapContent)
            horizontalPadding = dip(ROOT_LAYOUT_HORIZONTAL_PADDING)
            cardView {
                relativeLayout {
                    thumbnailImageView = imageView {
                        id = View.generateViewId()
                        backgroundResource = R.color.image_placeholder_background_color
                    }.lparams(matchParent, dip(THUMBNAIL_ICON_HEIGHT)) {
                        alignParentTop()
                        centerHorizontally()
                    }
                    relativeLayout {
                        titleValue = textView {
                            id = View.generateViewId()
                            textColorResource = R.color.video_list_item_title_color
                            ellipsize = TextUtils.TruncateAt.END
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP, TITLE_TEXT_SIZE)
                        }.lparams(wrapContent, wrapContent) {
                            centerHorizontally()
                        }
                        durationValue = textView {}.lparams {
                            below(titleValue)
                            centerHorizontally()
                            topMargin = dip(DURATION_TOP_MARGIN)
                        }
                    }.lparams(matchParent, wrapContent) {
                        below(thumbnailImageView)
                        topMargin = dip(INFO_CONTAINER_TOP_MARGIN)
                        bottomMargin = dip(INFO_CONTAINER_BOTTOM_MARGIN)
                    }
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, wrapContent)
        }
    }
}