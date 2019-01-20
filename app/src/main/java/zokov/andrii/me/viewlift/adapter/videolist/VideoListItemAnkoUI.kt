package zokov.andrii.me.viewlift.adapter.videolist

import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.*
import zokov.andrii.me.viewlift.R

class VideoListItemAnkoUI : AnkoComponent<ViewGroup> {
    companion object {
        private const val ROOT_LAYOUT_PADDING = 10
        private const val ROOT_LAYOUT_HEIGHT = 110
        private const val THUMBNAIL_ICON_WIDTH = 162
        private const val THUMBNAIL_ICON_HEIGHT = 92
        private const val THUMBNAIL_ICON_MARGING_END = 10
        private const val TITLE_TEXT_SIZE = 17F
    }

    internal lateinit var thumbnailImageView: ImageView
    internal lateinit var titleValue: TextView
    internal lateinit var durationValue: TextView
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, dip(ROOT_LAYOUT_HEIGHT))
            padding = dip(ROOT_LAYOUT_PADDING)
            thumbnailImageView = imageView {
                id = View.generateViewId()
                backgroundResource = R.color.image_placeholder_background_color
            }.lparams(dip(THUMBNAIL_ICON_WIDTH), dip(THUMBNAIL_ICON_HEIGHT)) {
                alignParentStart()
                marginEnd = dip(THUMBNAIL_ICON_MARGING_END)
            }
            relativeLayout {
                titleValue = textView {
                    id = View.generateViewId()
                    includeFontPadding = false
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, TITLE_TEXT_SIZE)
                    textColorResource = R.color.video_list_item_title_color
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams(matchParent, wrapContent)
                durationValue = textView { includeFontPadding = false }.lparams { below(titleValue) }
            }.lparams(matchParent, wrapContent) { endOf(thumbnailImageView) }
        }
    }
}