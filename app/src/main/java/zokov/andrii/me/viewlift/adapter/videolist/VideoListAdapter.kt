package zokov.andrii.me.viewlift.adapter.videolist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.AnkoContext
import zokov.andrii.me.viewlift.model.VideoItemWrapper
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VideoListAdapter @Inject constructor() : RecyclerView.Adapter<VideoListItemViewHolder>() {

    companion object {
        private const val THUMBNAIL_CLICK_THROTTLE = 500L
    }

    val items: MutableList<VideoItemWrapper> = ArrayList()

    var itemClickConsumer: Consumer<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VideoListItemViewHolder {
        return VideoListItemAnkoUI().let {
            return@let VideoListItemViewHolder(it.createView(AnkoContext.create(parent.context, parent)), it)
        }
    }

    override fun onBindViewHolder(holder: VideoListItemViewHolder, position: Int) {
        holder.ui.apply {
            items[position].let { info ->
                titleValue.text = info.title
                durationValue.text = String.format(Locale.getDefault(), "%.1f s",info.duration)
                RxView.clicks(thumbnailImageView).throttleFirst(THUMBNAIL_CLICK_THROTTLE, TimeUnit.MILLISECONDS)
                    .subscribe {
                        itemClickConsumer?.let { consumer -> Observable.just(info.id!!).subscribe(consumer) }
                    }
                Picasso.get().load(info.thumbnailUrl).into(thumbnailImageView)
            }
        }
    }

    override fun getItemCount() = items.size

}