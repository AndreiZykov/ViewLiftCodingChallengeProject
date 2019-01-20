package zokov.andrii.me.viewlift.di

import android.content.Context
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import zokov.andrii.me.viewlift.R
import zokov.andrii.me.viewlift.network.ServerApi
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return TikXmlConverterFactory.create(
            TikXml.Builder()
                .exceptionOnUnreadXml(false).build()
        )
    }

    @Singleton
    @Provides
    fun provideServerApi(context: Context, converterFactory: Converter.Factory): ServerApi {
        return Retrofit.Builder().baseUrl(context.getString(R.string.server_api_url))
            .client(OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build().create(ServerApi::class.java)
    }

}