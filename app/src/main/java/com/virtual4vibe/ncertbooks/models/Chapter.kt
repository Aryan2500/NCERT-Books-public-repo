package com.virtual4vibe.ncertbooks.models

import java.io.Serializable

data class Chapter(
    val chapterNumber: String,
    val nameInEng: String,
    val nameInHindi: String,
    val engMlink: String,
    val hindiMLink: String
) : Serializable
