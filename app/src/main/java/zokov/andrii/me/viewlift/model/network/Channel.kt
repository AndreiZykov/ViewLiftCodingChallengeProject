package zokov.andrii.me.viewlift.model.network

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "channel")
class Channel {
    @PropertyElement(name = "title")
    var title: String? = null
    @Element(name = "item")
    var items : List<Item>? = null
}