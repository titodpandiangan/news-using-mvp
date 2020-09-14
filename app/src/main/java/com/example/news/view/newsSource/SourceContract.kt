package com.example.news.view.newsSource

import com.example.news.model.sources.SourcesItem

interface SourceContract {
    interface SourceView{
        fun showMessage (message : String)
        fun showProgresbar()
        fun hideProgresbar()
        fun showSource (list : ArrayList<SourcesItem>)
        fun hideSource()
        fun onClickListener()
        fun stopSwipe()
        fun showSearchView()
    }

    interface SourcePresenter{
        fun start()
        fun onLoadSource(param : HashMap<String,String>)
    }
}