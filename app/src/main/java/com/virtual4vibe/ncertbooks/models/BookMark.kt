package com.virtual4vibe.ncertbooks.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_table")
data class BookMark(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title:String,
    val pageNumber: Int,
    val file_name :String
)
