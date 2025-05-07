package com.nvshink.effectivetestcase.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.time.LocalDate

/**
 * A class which represent course.
 * @param id Unique ID of an course.
 * @param title Title of the course.
 * @param text Course description.
 * @param price Course price.
 * @param rate Course rating.
 * @param startDate Course start date.
 * @param hasLike Indicates whether the course has been added to favorites.
 * @param publishDate Date of publication of the course.
 */

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
