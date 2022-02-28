package com.virtual4vibe.ncertbooks.common

import android.os.Environment
import java.io.File

val FIREBASE_ROOT =
    "https://ncert-books-b22c1-default-rtdb.asia-southeast1.firebasedatabase.app/"
val DOC_STORAGE_PATH =
//    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path
    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + File.separator +"NcertBooksByVirtualVibe" ).path
val colorList = arrayListOf("#8e24aa", "#0d47a1", "#f57f17", "#f4511e", "#37474f", "#d81b60")
val UNITY_GAME_ID = "4623502"
val TEST_MODE = false
