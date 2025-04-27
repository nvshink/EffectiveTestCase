package com.nvshink.effectivetestcase.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Course (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("startDate")
    val startDate: LocalDate?,
    @SerializedName("hasLike")
    val hasLike: Boolean,
    @SerializedName("publishDate")
    val publishDate: LocalDate?
)
