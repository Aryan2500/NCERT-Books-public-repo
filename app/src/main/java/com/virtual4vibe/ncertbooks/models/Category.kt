package com.virtual4vibe.ncertbooks.models

data class Category(
    val id:String,
    val cat_title: String,
    val items: ArrayList<CategoryItem>?,
    var isOpen: Boolean = true
)