package com.nvshink.effectivetestcase.data.repository

import android.util.Log
import com.nvshink.effectivetestcase.data.model.Course
import com.nvshink.effectivetestcase.data.remote.CourseService
import com.nvshink.effectivetestcase.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(private val service: CourseService) :
    CourseRepository {
    override suspend fun getCoursesByPublishDateASC(): Flow<List<Course>> = flow {
        emit(service.getCourses("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download").courses.sortedBy { it.publishDate })
    }.flowOn(Dispatchers.IO)

    override suspend fun getCoursesByPublishDateDSC(): Flow<List<Course>> = flow {
       emit(
           service.getCourses("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download").courses.sortedByDescending { it.publishDate }
       )
    }.flowOn(Dispatchers.IO)
}

