package com.nvshink.effectivetestcase.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class which represents a favorite course to be saved to the database.
 * @param id Unique ID of an course.
 */

@Entity(tableName = "favorites")
data class FavoriteCourse(
    @PrimaryKey
    val id: Int
)
