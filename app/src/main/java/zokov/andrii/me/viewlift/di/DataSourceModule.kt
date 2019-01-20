package zokov.andrii.me.viewlift.di

import dagger.Module
import dagger.Provides
import zokov.andrii.me.viewlift.data.ApplicationRemoteDataSource
import zokov.andrii.me.viewlift.data.repository.ApplicationRemoteRepository
import zokov.andrii.me.viewlift.network.ServerApi
import javax.inject.Singleton

@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun provideApplicationRemoteDataSource(serverApi: ServerApi): ApplicationRemoteDataSource {
        return ApplicationRemoteRepository(serverApi)
    }
}