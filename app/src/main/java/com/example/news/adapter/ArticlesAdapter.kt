package com.example.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.model.articles.ArticlesItem
import com.example.news.model.category.CategoryModel
import com.example.news.model.sources.SourcesItem
import com.example.news.utils.FormatDateTime
import com.example.news.utils.ImageLoader
import kotlinx.android.synthetic.main.layout_item_articles.view.*
import kotlinx.android.synthetic.main.layout_item_category.view.*
import java.util.*
import kotlin.collections.ArrayList

class ArticlesAdapter (var listArticles : ArrayList<ArticlesItem>) :RecyclerView.Adapter<ArticlesAdapter.ViewHolder>(), Filterable {
    private lateinit var articlesListListener: ArticlesListListener
    lateinit var context: Context
    var listArticlesFiltered = ArrayList<ArticlesItem>()

    init {
        listArticlesFiltered=listArticles
    }

    fun setArticlesListener (articlesListListener: ArticlesListListener){
        this.articlesListListener=articlesListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        val view = layoutInflater.inflate(R.layout.layout_item_articles, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listArticlesFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitleArticles.text=listArticlesFiltered[position].title
        holder.tvDate.text=listArticlesFiltered[position].publishedAt
        listArticlesFiltered[position].urlToImage?.let { ImageLoader.loadFromUrl(context, it,holder.imgArticles) }
        holder.tvDate.text=FormatDateTime.formatDate(listArticlesFiltered[position].publishedAt) +" " + FormatDateTime.formatTime(listArticlesFiltered[position].publishedAt)
        holder.itemView.setOnClickListener {
            articlesListListener.onClickItemArticles(listArticlesFiltered[position])
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listArticlesFiltered = listArticles
                } else {
                    val filteredList: ArrayList<ArticlesItem> = ArrayList()
                    for (row in listArticles) {
                        if (row.title!!.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(
                                Locale.ROOT
                            )
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    listArticlesFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listArticlesFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listArticlesFiltered = filterResults.values as ArrayList<ArticlesItem>
                notifyDataSetChanged()
            }
        }
    }


    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        internal var imgArticles = itemView.imgArticles
        internal var tvTitleArticles = itemView.tvTitleArticles
        internal var tvDate = itemView.tvTime

    }

    interface ArticlesListListener{
        fun onClickItemArticles (articlesItem: ArticlesItem)
    }
}