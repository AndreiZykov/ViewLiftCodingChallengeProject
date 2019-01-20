package zokov.andrii.me.viewlift.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import zokov.andrii.me.viewlift.ViewLiftApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, AndroidInjectionModule::class, ActivityModule::class,
        RetrofitModule::class, DataSourceModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindApplication(application: ViewLiftApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: ViewLiftApplication)
}
