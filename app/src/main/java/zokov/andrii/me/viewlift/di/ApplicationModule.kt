package zokov.andrii.me.viewlift.di

import android.content.Context
import dagger.Module
import dagger.Provides
import zokov.andrii.me.viewlift.ViewLiftApplication
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: ViewLiftApplication): Context {
        return application
    }


}