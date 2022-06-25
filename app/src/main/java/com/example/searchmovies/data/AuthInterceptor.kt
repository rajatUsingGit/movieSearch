package com.example.searchmovies.data

import com.example.searchmovies.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", Constants.AUTH_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}