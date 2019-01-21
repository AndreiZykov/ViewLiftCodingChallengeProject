package zokov.andrii.me.viewlift.model.network

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
class Rss {
    @Element(name = "channel")
    var channel: Channel? = null
}

