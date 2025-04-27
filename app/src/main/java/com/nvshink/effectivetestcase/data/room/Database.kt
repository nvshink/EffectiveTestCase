package com.nvshink.effectivetestcase.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nvshink.effectivetestcase.data.dao.FavoriteCourseDao
import com.nvshink.effectivetestcase.data.model.FavoriteCourse

@Database(
    entities = [FavoriteCourse::class],
    version = 1,
    exportSchema = true
)
abstract class Database: RoomDatabase() {
    abstract val favoriteCourseDao: FavoriteCourseDao
}