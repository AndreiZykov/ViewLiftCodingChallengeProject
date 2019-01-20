package zokov.andrii.me.viewlift.network

import io.reactivex.Single
import retrofit2.http.GET
import zokov.andrii.me.viewlift.model.network.Rss

interface ServerApi {
    @GET("feed_firetv.xml")
    fun fetchRss(): Single<Rss>
}
