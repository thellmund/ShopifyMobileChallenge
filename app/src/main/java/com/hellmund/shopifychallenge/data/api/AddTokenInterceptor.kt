package com.hellmund.shopifychallenge.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AddTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val modifiedUrl = url.newBuilder().addQueryParameter("access_token", ACCESS_TOKEN).build()
        val modifiedRequest = request.newBuilder().url(modifiedUrl).build()
        return chain.proceed(modifiedRequest)
    }

    companion object {
        private const val ACCESS_TOKEN = "c32313df0d0ef512ca64d5b336a0d7c6"
    }

}
