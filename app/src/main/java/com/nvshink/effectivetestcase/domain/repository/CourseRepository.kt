package com.nvshink.effectivetestcase.domain.repository

import com.nvshink.effectivetestcase.data.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCoursesByPublishDateASC(): Flow<List<Course>>
    suspend fun getCoursesByPublishDateDSC(): Flow<List<Course>>
}