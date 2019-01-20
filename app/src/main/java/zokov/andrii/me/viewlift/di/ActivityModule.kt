package zokov.andrii.me.viewlift.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import zokov.andrii.me.viewlift.activity.videolist.VideoListActivity
import zokov.andrii.me.viewlift.activity.videolist.VideoListActivitySubComponent

@Module(subcomponents = [VideoListActivitySubComponent::class])
internal abstract class ActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(VideoListActivity::class)
    internal abstract fun createAndroidInjectionFactory4VideoListActivity(builder: VideoListActivitySubComponent.Builder):
            AndroidInjector.Factory<out Activity>

}