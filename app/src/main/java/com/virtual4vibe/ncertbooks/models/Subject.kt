package com.virtual4vibe.ncertbooks.models

import java.io.Serializable

data class Subject(
    val subject_id :String,
    val subjectNumber: String,
    val subjectNameInEng: String,
    val subjectNameInHin: String = ""
):Serializable