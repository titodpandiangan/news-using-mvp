package com.example.news.view.newsArticles

import com.example.news.model.articles.ArticlesItem
import com.example.news.model.sources.SourcesItem

interface ArticlesContract {
    interface ArticlesView{
        fun showMessage (message : String)
        fun showProgresbar()
        fun hideProgresbar()
        fun showArticles (list : ArrayList<ArticlesItem>, page : Int)
        fun hideSource()
        fun onClickListener()
        fun recycleviewListener()
        fun getParamLoadArticles () : HashMap<String,Any>
        fun showSearchView()
    }

    interface ArticlesPresenter{
        fun start()
        fun onLoadArticles()
        fun onLoadMore(page:Int)
        fun checkLoadMore(visibleItemCount:Int, totalItemCount:Int, pastVisibleItems:Int)
    }
}