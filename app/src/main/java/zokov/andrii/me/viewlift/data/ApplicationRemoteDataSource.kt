package zokov.andrii.me.viewlift.data

import io.reactivex.Single
import zokov.andrii.me.viewlift.model.VideoItemWrapper

interface ApplicationRemoteDataSource {
    fun fetchContentItems(): Single<List<VideoItemWrapper>>
}