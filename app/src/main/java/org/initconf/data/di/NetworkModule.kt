package org.initconf.data.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.initconf.data.TalksService
import org.initconf.data.call.ResultCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val size: Long = 10 * 1024 * 1024
        return Cache(context.cacheDir, size)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Reusable
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultCallAdapterFactory)
            .client(client)
            .baseUrl("https://raw.githubusercontent.com/VladimirWrites/InitConf2022/main/")
            .build()
    }

    @Provides
    @Singleton
    fun provideTalksService(retrofit: Retrofit): TalksService {
        return retrofit.create(TalksService::class.java)
    }
}