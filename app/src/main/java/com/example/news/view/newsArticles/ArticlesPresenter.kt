package com.example.news.view.newsArticles

import android.util.Log
import com.example.news.model.articles.ArticlesItem
import com.example.news.model.articles.ResponseArticlesData
import com.example.news.service.api.MainAPI
import com.example.news.utils.Constant

class ArticlesPresenter (var view : ArticlesContract.ArticlesView) : ArticlesContract.ArticlesPresenter, MainAPI.ArticlesListener {
    lateinit var mainAPI: MainAPI

    var currPage=1
    var hasNoMore:Boolean=false
    var isLoadingMore:Boolean=true
    var articlesData= ArrayList<ArticlesItem>()

    override fun start() {
        mainAPI= MainAPI()
        mainAPI.setArticlesListener(this)
        view.onClickListener()
        view.recycleviewListener()
    }

    override fun onLoadArticles() {
        view.showProgresbar()
        mainAPI.getArticles(view.getParamLoadArticles(),currPage)
    }

    override fun onLoadMore(page: Int) {
        mainAPI.getArticles(view.getParamLoadArticles(),page)
    }

    override fun checkLoadMore(visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (hasNoMore || isLoadingMore){
            return
        }
        else{
            if (pastVisibleItems+visibleItemCount>=totalItemCount){
                isLoadingMore=true
                currPage+=1
                mainAPI.getArticles(view.getParamLoadArticles(),currPage)
            }
        }
    }

    override fun onSuccessResponseArticles(responseArticlesData: ResponseArticlesData) {

        view.hideProgresbar()
        view.showSearchView()

        if (responseArticlesData.status.equals(Constant.RESPOND_OK)){
            if (currPage==1){
                if (responseArticlesData.articles?.size==0){
                    view.showMessage("Articles is empty.")
                }

                else{
                    articlesData.clear()
                }
            }

            for (currData in responseArticlesData.articles!!){
                currData?.let { articlesData.add(it) }
                isLoadingMore=false
                hasNoMore= responseArticlesData.articles.isEmpty()
            }

            view.showArticles(articlesData,currPage)
        }

        else{

            responseArticlesData.message?.let { view.showMessage(it) }
        }
    }

    override fun onFailResponseArticles(message: String) {
        view.hideProgresbar()
        view.showMessage(message)
    }
}