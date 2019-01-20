package com.hellmund.shopifychallenge.di

import android.content.Context
import com.hellmund.shopifychallenge.data.api.AddTokenInterceptor
import com.hellmund.shopifychallenge.data.api.ShopifyApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideCache(
        context: Context
    ): Cache {
        return Cache(context.cacheDir, 10 * 1024 * 1024) // 10 MB
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(AddTokenInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideShopifyApiService(
        okHttpClient: OkHttpClient
    ): ShopifyApiService {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopifyApiService::class.java)
    }

    companion object {
        private const val API_BASE_URL = "https://shopicruit.myshopify.com/admin/"
    }

}
