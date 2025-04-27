package com.nvshink.effectivetestcase.data.remote

import com.nvshink.effectivetestcase.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface CourseService {
    @GET
    suspend fun getCourses(@Url url: String): ApiResponse.ApiResponse
}