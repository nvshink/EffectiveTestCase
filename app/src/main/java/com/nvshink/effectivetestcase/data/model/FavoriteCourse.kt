package com.nvshink.effectivetestcase.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCourse(
    @PrimaryKey
    val id: Int
)
