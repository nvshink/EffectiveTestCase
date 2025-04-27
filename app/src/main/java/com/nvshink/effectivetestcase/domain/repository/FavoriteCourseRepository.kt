package com.nvshink.effectivetestcase.domain.repository

import com.nvshink.effectivetestcase.data.model.FavoriteCourse
import kotlinx.coroutines.flow.Flow

interface FavoriteCourseRepository {
    suspend fun upsertFavoriteCourse(favoriteCourse: FavoriteCourse)
    suspend fun deleteFavoriteCourse(favoriteCourse: FavoriteCourse)
    fun getFavoriteCoursesById(): Flow<List<FavoriteCourse>>
    suspend fun isFavoriteCourse(id: Int): Boolean
}