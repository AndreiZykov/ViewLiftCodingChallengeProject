package zokov.andrii.me.viewlift.model.network

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "media:thumbnail")
class ItemThumbnail {
    @Attribute(name = "url")
    var thumbnail: String? = null
}