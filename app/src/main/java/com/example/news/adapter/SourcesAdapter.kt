package com.example.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.sources.SourcesItem
import kotlinx.android.synthetic.main.layout_item_sources.view.*
import java.util.*
import kotlin.collections.ArrayList

class SourcesAdapter(var listSources : ArrayList<SourcesItem>) : RecyclerView.Adapter<SourcesAdapter.ViewHolder>(), Filterable {

    private lateinit var sourceListListener: SourceListListener
    var listSourcesFiltered = ArrayList<SourcesItem>()

    init {
        listSourcesFiltered = listSources
    }

    fun setSourceListener(sourceListListener: SourceListListener){
        this.sourceListListener=sourceListListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_sources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSourcesFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSourcesName.text=listSourcesFiltered[position].name
        holder.tvUrlSources.text=listSourcesFiltered[position].url

        holder.itemView.setOnClickListener {
            sourceListListener.onClickItemSource(listSourcesFiltered[position])
        }
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        internal var tvSourcesName = itemView.tvSourcesName
        internal var tvUrlSources = itemView.tvUrlSources
    }

    interface SourceListListener{
        fun onClickItemSource (sourcesItem: SourcesItem)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listSourcesFiltered = listSources
                } else {
                    val filteredList: ArrayList<SourcesItem> = ArrayList()
                    for (row in listSources) {
                        if (row.name!!.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(
                                Locale.ROOT
                            )
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    listSourcesFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listSourcesFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listSourcesFiltered = filterResults.values as ArrayList<SourcesItem>
                notifyDataSetChanged()
            }
        }
    }
}