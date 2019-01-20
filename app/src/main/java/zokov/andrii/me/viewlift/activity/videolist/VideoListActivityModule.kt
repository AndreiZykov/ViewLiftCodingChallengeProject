package zokov.andrii.me.viewlift.activity.videolist

import dagger.Binds
import dagger.Module
import zokov.andrii.me.viewlift.di.scope.PerActivity

@Module
abstract class VideoListActivityModule {

    @Binds
    @PerActivity
    abstract fun bindView(view: VideoListActivity): VideoListActivityContract.View

    @Binds
    @PerActivity
    abstract fun bindPresenter(presenter: VideoListPresenter): VideoListActivityContract.Presenter

}