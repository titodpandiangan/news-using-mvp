package com.example.news.model.sources

import com.google.gson.annotations.SerializedName

data class ResponseSourcesData(

	@field:SerializedName("sources")
	val sources: List<SourcesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)