package zokov.andrii.me.viewlift.model.network

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "media:content")
class ItemContent {
    @PropertyElement(name = "media:title")
    var title: String? = null
    @Element(name = "media:thumbnail")
    var thumbnail: ItemThumbnail? = null
    @Attribute(name = "url")
    var videoUrl: String? = null
    @Attribute(name = "duration")
    var duration: Double? = null
}