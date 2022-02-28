package com.virtual4vibe.ncertbooks.common

import android.util.Log
import androidx.core.net.toUri
import java.io.File


object FileExist {

    fun check(filename: String): Any {
        var file = File(
            DOC_STORAGE_PATH + File.separator + filename + ".pdf"
        )
        Log.v("fileurl", "${file.toUri()}")
        if (file.exists()) {
            return file.toUri()
        }
        return false
    }

}
