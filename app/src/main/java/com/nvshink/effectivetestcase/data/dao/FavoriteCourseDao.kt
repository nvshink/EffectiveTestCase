package com.nvshink.effectivetestcase.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nvshink.effectivetestcase.data.model.FavoriteCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCourseDao {
    @Upsert
    suspend fun upsertFavoriteCourse (favoriteCourse: FavoriteCourse)
    @Delete
    suspend fun deleteFavoriteCourse (favoriteCourse: FavoriteCourse)
    @Query("SELECT * FROM favorites ORDER BY id ASC")
    fun getCoursesById(): Flow<List<FavoriteCourse>>
    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :id)")
    suspend fun isFavoriteCourse(id: Int): Boolean
}