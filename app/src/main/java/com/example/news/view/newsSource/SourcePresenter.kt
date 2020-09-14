package com.example.news.view.newsSource

import com.example.news.model.sources.ResponseSourcesData
import com.example.news.model.sources.SourcesItem
import com.example.news.service.api.MainAPI
import com.example.news.utils.Constant

class SourcePresenter (var view : SourceContract.SourceView) : SourceContract.SourcePresenter, MainAPI.SourcesListener {
    lateinit var mainAPI: MainAPI

    override fun start() {
        mainAPI= MainAPI()
        mainAPI.setSourcesListener(this)
        view.onClickListener()
    }

    override fun onLoadSource(param: HashMap<String, String>) {
        view.showProgresbar()
        mainAPI.getSources(param)
    }

    override fun onSuccessResponseSources(responseSourcesData: ResponseSourcesData) {
        view.hideProgresbar()
        view.stopSwipe()
        view.showSearchView()
        if (responseSourcesData.status.equals(Constant.RESPOND_OK)){
            view.showSource(responseSourcesData.sources as ArrayList<SourcesItem>)
        }
        else{
           responseSourcesData.message?.let { view.showMessage(it) }
        }
    }

    override fun onFailResponseSources(message: String) {
        view.stopSwipe()
        view.hideProgresbar()
        view.showMessage(message)
    }
}