package com.virtual4vibe.ncertbooks.common

import android.content.Context
import android.graphics.Color
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.virtual4vibe.ncertbooks.models.Class
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun getRandomColor(): Int {
    val randNumber = (0..5).random()
    return  Color.parseColor(colorList[randNumber])
}


fun fbRef(): DatabaseReference {

    return FirebaseDatabase.getInstance(FIREBASE_ROOT).reference
}

fun loadJSONFromAsset(c:Context): String? {
    var json: String? = null
    json = try {
        val iss: InputStream = c.assets.open("data.json")
        val size: Int = iss.available()
        val buffer = ByteArray(size)
        iss.read(buffer)
        iss.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun createClassList(classArray : List<String>): ArrayList<Class> {
    var classList = ArrayList<Class>()
    classList.clear()
    classArray.forEach {
        classList.add(Class(it))
    }
    return classList
}

