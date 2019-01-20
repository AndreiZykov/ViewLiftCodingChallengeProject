package zokov.andrii.me.viewlift.data.repository

import io.reactivex.Single
import zokov.andrii.me.viewlift.data.ApplicationRemoteDataSource
import zokov.andrii.me.viewlift.model.VideoItemWrapper
import zokov.andrii.me.viewlift.network.ServerApi

class ApplicationRemoteRepository constructor(private val serverApi: ServerApi) : ApplicationRemoteDataSource {
    override fun fetchContentItems(): Single<List<VideoItemWrapper>> {
        return serverApi.fetchRss().map { it.chanel?.items ?: ArrayList() }.map { items ->
            items.map { VideoItemWrapper.of(it) } }
    }
}