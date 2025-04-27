package com.nvshink.effectivetestcase.data.repository

import com.nvshink.effectivetestcase.data.dao.FavoriteCourseDao
import com.nvshink.effectivetestcase.data.model.FavoriteCourse
import com.nvshink.effectivetestcase.domain.repository.FavoriteCourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteCourseRepositoryImpl @Inject constructor (private val dao: FavoriteCourseDao): FavoriteCourseRepository {
    override suspend fun upsertFavoriteCourse(favoriteCourse: FavoriteCourse) = dao.upsertFavoriteCourse(favoriteCourse = favoriteCourse)
    override suspend fun deleteFavoriteCourse(favoriteCourse: FavoriteCourse) = dao.deleteFavoriteCourse(favoriteCourse = favoriteCourse)
    override fun getFavoriteCoursesById(): Flow<List<FavoriteCourse>> = dao.getCoursesById()
    override suspend fun isFavoriteCourse(id: Int) = dao.isFavoriteCourse(id = id)
}