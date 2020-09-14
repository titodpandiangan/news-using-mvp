package com.example.news.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.CategoryAdapter
import com.example.news.model.category.CategoryModel
import com.example.news.view.newsSource.SourcesActivity
import java.util.*
import kotlin.collections.ArrayList
import java.security.AccessController.getContext as getContext1

class MainActivity : AppCompatActivity() {
    lateinit var rvCategory : RecyclerView
    lateinit var categoryAdapter : CategoryAdapter

    lateinit var dividerItemDecoration: DividerItemDecoration
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var listCategory : ArrayList<CategoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initCategory()
        showCategory()
    }

    fun initView(){
        rvCategory=findViewById(R.id.rvCategory)
        mLayoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    }

    fun initCategory(){
        listCategory= ArrayList()

        listCategory.add(CategoryModel("business","Business"))
        listCategory.add(CategoryModel("entertainment","Entertainment"))
        listCategory.add(CategoryModel("general","General"))
        listCategory.add(CategoryModel("health","Health"))
        listCategory.add(CategoryModel("science","Science"))
        listCategory.add(CategoryModel("sports","Sports"))
        listCategory.add(CategoryModel("technology","Technology"))
    }

    fun showCategory(){
        rvCategory.layoutManager = mLayoutManager
        rvCategory.itemAnimator = DefaultItemAnimator()
        rvCategory.addItemDecoration(dividerItemDecoration)

        categoryAdapter= CategoryAdapter(listCategory)
        categoryAdapter.setCategoryListListener(object : CategoryAdapter.CategoryListListener{
            override fun onClickItemCategory(categoryModel: CategoryModel) {
                val intent = Intent(this@MainActivity, SourcesActivity::class.java)
                intent.putExtra("category", categoryModel.id)
                startActivity(intent)
            }

        })

        rvCategory.adapter=categoryAdapter
        categoryAdapter.notifyDataSetChanged()
    }
}