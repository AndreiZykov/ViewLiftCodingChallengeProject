package zokov.andrii.me.viewlift.model

import zokov.andrii.me.viewlift.model.network.Item

class VideoItemWrapper constructor(val title: String, val thumbnailUrl: String,
    val duration: Double, val videoUrl: String) {
    var id: Long? = null
    companion object {
        fun of(item: Item): VideoItemWrapper {
            return VideoItemWrapper(item.title ?: "", item.itemContent?.thumbnail?.thumbnail ?: "",
                item.itemContent?.duration ?: 0.0 , item.itemContent?.videoUrl ?: ""
            )
        }
    }
}