package com.example.news.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FormatDateTime {
    companion object{
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formatDate = SimpleDateFormat("MMMM dd, yyyy")
        val formatTime = SimpleDateFormat("HH:mm")

        fun formatDate(strDate: String?): String? {
            var strFormatDate: String? = null
            try {
                val date: Date =utcFormat.parse(strDate)
                strFormatDate = formatDate.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return strFormatDate
        }

        fun formatTime(strDate: String?): String? {
            var strFormatDate: String? = null
            try {
                val date: Date =utcFormat.parse(strDate)
                strFormatDate =formatTime.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return strFormatDate
        }
    }
}