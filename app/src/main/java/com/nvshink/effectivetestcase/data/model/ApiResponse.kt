package com.nvshink.effectivetestcase.data.model

import com.squareup.moshi.JsonClass

open class ApiResponse {
    @JsonClass(generateAdapter = true)
    data class ApiResponse(
        val courses: List<Course>
    )
}