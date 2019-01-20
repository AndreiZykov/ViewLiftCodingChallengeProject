package zokov.andrii.me.viewlift.activity.videolist

import dagger.Subcomponent
import dagger.android.AndroidInjector
import zokov.andrii.me.viewlift.di.scope.PerActivity


@PerActivity
@Subcomponent(modules = [VideoListActivityModule::class])
interface VideoListActivitySubComponent : AndroidInjector<VideoListActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<VideoListActivity>()

}