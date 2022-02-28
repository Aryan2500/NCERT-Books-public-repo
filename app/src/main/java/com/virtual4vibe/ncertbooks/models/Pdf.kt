package com.virtual4vibe.ncertbooks.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdf_table")
data class Pdf(@PrimaryKey(autoGenerate = true) val id: Int, val title: String, val pdfname: String)
