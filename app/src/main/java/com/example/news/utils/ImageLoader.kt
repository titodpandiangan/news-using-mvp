package com.example.news.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.news.R


class ImageLoader {
    companion object{
        fun loadFromUrl (context : Context , url : String, imageView: ImageView){
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_broken)

            Glide.with(context).load(url).apply(options)
                .centerCrop()
                .into(imageView)
        }
    }
}