package com.example.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.category.CategoryModel
import kotlinx.android.synthetic.main.layout_item_category.view.*

class CategoryAdapter (var listCategory : List<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var categoryListListener: CategoryListListener

    fun setCategoryListListener(categoryListListener: CategoryListListener){
        this.categoryListListener=categoryListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCategoryName.text=listCategory[position].name

        holder.itemView.setOnClickListener {
            categoryListListener.onClickItemCategory(listCategory[position])
        }
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        internal var tvCategoryName = itemView.tvCategoryName

    }

    interface CategoryListListener{
        fun onClickItemCategory (categoryModel: CategoryModel)
    }

}