package com.example.searchmovies.data.remote

import com.example.searchmovies.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WebApiService {
    @GET("search/movie")
    suspend fun getSearchResults(@Query("query") query: String) : Response<WebResponse>
}

private val sRetrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build())
    .build()

object WebApi {
    val retrofitService: WebApiService by lazy { sRetrofit.create(WebApiService::class.java) }
}