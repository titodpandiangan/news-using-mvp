package com.example.news.service.api

import android.content.Context
import com.example.news.model.articles.ResponseArticlesData
import com.example.news.model.sources.ResponseSourcesData
import com.example.news.service.NetworkNews
import com.example.news.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAPI (){
    private var mainService = NetworkNews.client().create(MainService::class.java)
    private lateinit var sourcesListener: SourcesListener
    private lateinit var articlesListener: ArticlesListener

    fun setSourcesListener(sourcesListener: SourcesListener){
        this.sourcesListener=sourcesListener
    }

    fun setArticlesListener(articlesListener: ArticlesListener){
        this.articlesListener=articlesListener
    }

    fun getSources(param : HashMap<String,String>){
        mainService.getSources(param).enqueue(object : Callback<ResponseSourcesData>{
            override fun onFailure(call: Call<ResponseSourcesData>, t: Throwable) {
                t.message?.let { sourcesListener.onFailResponseSources(it) }
            }

            override fun onResponse(
                call: Call<ResponseSourcesData>,
                response: Response<ResponseSourcesData>
            ) {
                response.body()?.let { sourcesListener.onSuccessResponseSources(it) }
            }

        })
    }

    fun getArticles(param : HashMap<String,Any>, page : Int){
        param["page"] = page
        mainService.getArticles(param).enqueue(object : Callback<ResponseArticlesData>{
            override fun onFailure(call: Call<ResponseArticlesData>, t: Throwable) {
                t.message?.let { articlesListener.onFailResponseArticles(it) }
            }

            override fun onResponse(
                call: Call<ResponseArticlesData>,
                response: Response<ResponseArticlesData>
            ) {
                response.body()?.let { articlesListener.onSuccessResponseArticles(it) }
            }

        })
    }

    interface SourcesListener{
        fun onSuccessResponseSources(responseSourcesData: ResponseSourcesData)
        fun onFailResponseSources(message : String)
    }

    interface ArticlesListener{
        fun onSuccessResponseArticles(responseArticlesData: ResponseArticlesData)
        fun onFailResponseArticles(message : String)
    }
}