package com.example.news.view.newsArticles

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.R
import com.example.news.adapter.ArticlesAdapter
import com.example.news.adapter.SourcesAdapter
import com.example.news.model.articles.ArticlesItem
import com.example.news.utils.Constant
import com.example.news.view.newsSource.SourcePresenter
import com.example.news.view.webView.WebViewActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ArticlesActivity : AppCompatActivity() , ArticlesContract.ArticlesView {
    lateinit var pbProses : ProgressBar
    lateinit var layMessage : LinearLayout
    lateinit var rvArticles : RecyclerView
    lateinit var tvMessage : TextView
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var dividerItemDecoration: DividerItemDecoration
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var toolbar: Toolbar
    lateinit var searchView : SearchView

    lateinit var articlesPresenter: ArticlesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        (findViewById<View>(R.id.textViewTitleAppBar) as TextView).text = intent.getStringExtra("name")
        initView()
        initPresenter()
    }

    fun initView(){
        pbProses=findViewById(R.id.progressBar)
        layMessage=findViewById(R.id.layMessage)
        rvArticles=findViewById(R.id.rvArticles)
        tvMessage=findViewById(R.id.tvMessage)
        toolbar=findViewById(R.id.toolbar)
        searchView = findViewById(R.id.searchView)

        mLayoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    }

    fun initPresenter(){
        articlesPresenter= ArticlesPresenter(this)
        articlesPresenter.start()
        articlesPresenter.onLoadArticles()
    }

    override fun showMessage(message: String) {
        pbProses.visibility= View.INVISIBLE
        layMessage.visibility= View.VISIBLE
        tvMessage.text=message
    }

    override fun showProgresbar() {
        pbProses.visibility=View.VISIBLE
        layMessage.visibility= View.INVISIBLE
    }

    override fun hideProgresbar() {
        pbProses.visibility=View.INVISIBLE
    }

    override fun showArticles(list: ArrayList<ArticlesItem>, page: Int) {
        if (page==1){
            mLayoutManager=
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dividerItemDecoration = DividerItemDecoration(
                Objects.requireNonNull(this), DividerItemDecoration.VERTICAL
            )
            rvArticles.layoutManager = mLayoutManager
            rvArticles.itemAnimator = DefaultItemAnimator()

            articlesAdapter=ArticlesAdapter(list)

            articlesAdapter.setArticlesListener(object : ArticlesAdapter.ArticlesListListener{
                override fun onClickItemArticles(articlesItem: ArticlesItem) {
                    val intent = Intent(this@ArticlesActivity, WebViewActivity::class.java)
                    intent.putExtra("name", articlesItem.source!!.name)
                    intent.putExtra("url", articlesItem.url)
                    startActivity(intent)
                }

            })

            rvArticles.adapter = articlesAdapter
            articlesAdapter.notifyDataSetChanged()
        }

        else{
            articlesAdapter.notifyDataSetChanged()
        }
    }


    override fun hideSource() {
        rvArticles.visibility=View.INVISIBLE
    }

    override fun onClickListener() {
        toolbar.setNavigationOnClickListener { v: View? -> finish() }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                articlesAdapter.filter.filter(newText)
                return false
            }

        })
    }


    override fun recycleviewListener() {
        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position =
                    (Objects.requireNonNull(recyclerView.layoutManager) as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                articlesPresenter.checkLoadMore(recyclerView.layoutManager!!.childCount,
                    recyclerView.layoutManager!!.itemCount, position )
            }
        })
    }

    override fun getParamLoadArticles(): HashMap<String, Any> {
        val param = HashMap<String,Any>()
        param["apiKey"]= Constant.BASE_API_KEY
        param["sources"]=intent.getStringExtra("sources")!!
        return param
    }

    override fun showSearchView() {
       searchView.visibility=View.VISIBLE
    }
}