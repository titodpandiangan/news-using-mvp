package com.example.news.service.api

import com.example.news.model.articles.ResponseArticlesData
import com.example.news.model.sources.ResponseSourcesData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MainService {
    @GET("sources")
    fun getSources(
        @QueryMap parameter: Map<String, String>
    ): Call<ResponseSourcesData>

    @JvmSuppressWildcards
    @GET("everything")
    fun getArticles(
        @QueryMap parameter: Map<String, Any>
    ): Call<ResponseArticlesData>
}