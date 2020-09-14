package com.example.news.view.newsSource

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.R
import com.example.news.adapter.SourcesAdapter
import com.example.news.model.sources.SourcesItem
import com.example.news.utils.Constant
import com.example.news.view.newsArticles.ArticlesActivity

class SourcesActivity : AppCompatActivity(), SourceContract.SourceView {
    lateinit var pbProses : ProgressBar
    lateinit var layMessage : LinearLayout
    lateinit var rvSource : RecyclerView
    lateinit var tvMessage : TextView
    lateinit var swipe : SwipeRefreshLayout
    lateinit var sourcesAdapter: SourcesAdapter
    lateinit var dividerItemDecoration: DividerItemDecoration
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var toolbar :Toolbar
    lateinit var searchView : SearchView

    lateinit var sourcePresenter: SourcePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)
        (findViewById<View>(R.id.textViewTitleAppBar) as TextView).text = intent.getStringExtra("category")
        initView()
        initPresenter()
    }

    fun initView(){
        pbProses=findViewById(R.id.progressBar)
        layMessage=findViewById(R.id.layMessage)
        rvSource=findViewById(R.id.rvSources)
        swipe=findViewById(R.id.swipe)
        tvMessage=findViewById(R.id.tvMessage)
        toolbar=findViewById(R.id.toolbar)
        searchView = findViewById(R.id.searchView)

        mLayoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    }

    fun initPresenter(){
        sourcePresenter= SourcePresenter(this)
        sourcePresenter.start()

        sourcePresenter.onLoadSource(getParamToLoadSource())
    }

    override fun showMessage(message: String) {
        pbProses.visibility=View.INVISIBLE
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

    override fun showSource(list: ArrayList<SourcesItem>) {
        rvSource.visibility=View.VISIBLE

        rvSource.layoutManager = mLayoutManager
        rvSource.itemAnimator = DefaultItemAnimator()
        sourcesAdapter= SourcesAdapter(list)
        sourcesAdapter.setSourceListener(object : SourcesAdapter.SourceListListener{
            override fun onClickItemSource(sourcesItem: SourcesItem) {
                val intent = Intent(this@SourcesActivity, ArticlesActivity::class.java)
                intent.putExtra("sources", sourcesItem.id)
                intent.putExtra("name", sourcesItem.name)
                startActivity(intent)
            }

        })

        rvSource.adapter=sourcesAdapter
        sourcesAdapter.notifyDataSetChanged()
    }

    override fun hideSource() {
        rvSource.visibility=View.GONE
    }

    override fun onClickListener() {
        swipe.setOnRefreshListener {
            sourcePresenter.onLoadSource(getParamToLoadSource())
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sourcesAdapter.filter.filter(newText)
                return false
            }

        })

        toolbar.setNavigationOnClickListener { v: View? -> finish() }
    }

    override fun stopSwipe() {
        swipe.isRefreshing=false
    }

    fun getParamToLoadSource(): HashMap<String, String> {
        val param = HashMap<String,String>()
        param["apiKey"]=Constant.BASE_API_KEY
        param["category"]=intent.getStringExtra("category")!!
        return param
    }

    override fun showSearchView() {
        searchView.visibility=View.VISIBLE
    }
}