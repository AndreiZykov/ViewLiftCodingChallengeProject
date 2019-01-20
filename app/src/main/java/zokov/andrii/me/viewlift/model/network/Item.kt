package zokov.andrii.me.viewlift.model.network

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
class Item constructor() {
    @PropertyElement(name = "title")
    var title: String? = null
    @Element(name = "media:content")
    var itemContent: ItemContent? = null
}