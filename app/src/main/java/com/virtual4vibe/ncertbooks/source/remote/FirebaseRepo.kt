package com.virtual4vibe.ncertbooks.source.remote

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.virtual4vibe.ncertbooks.common.createClassList
import com.virtual4vibe.ncertbooks.common.fbRef
import com.virtual4vibe.ncertbooks.models.Chapter
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.models.Subject

class FirebaseRepo {

    fun getClassList(_classList: MutableLiveData<List<Class>>) {
        val db = fbRef().child("categories").child("1").child("items").child("1")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                var classString = ""
                classString = ds.child("classes").value.toString()
                Log.v("classString", "${ds.child("classes")}")
                _classList.postValue(createClassList(classString.split(",")))
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    fun getSubjectsList(
        classs: String,
        stream: String = "",
        _subjecctList: MutableLiveData<List<Subject>>
    ) {
        val subjectList = ArrayList<Subject>()
        val db = fbRef().child("subjects").child("class $classs").child(stream)

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(ds: DataSnapshot) {
                  ds.children.forEach { sub ->
                    subjectList.add(
                        Subject(
                            sub.child("subject_id").value.toString(),
                            sub.child("number").value.toString(),
                            sub.child("nameInEng").value.toString(),
                            sub.child("nameInHindi").value.toString(),

                            )
                    )
                }
                _subjecctList.postValue(subjectList)
            }

            override fun onCancelled(p0: DatabaseError) = Unit

        })
    }

    fun getTextBookChapters(subject_id: String, _chapterList: MutableLiveData<List<Chapter>>) {
        val chapterList = ArrayList<Chapter>()

        val db = fbRef().child("Books").child("chapters").child(subject_id)
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                ds.children.forEach { chapter ->
                    Log.v("chapter", "${chapter.value}")

                    chapterList.add(
                        Chapter(
                            chapter.child("number").value.toString(),
                            chapter.child("nameInEng").value.toString(),
                            chapter.child("nameInHindi").value.toString(),
                            chapter.child("engMLink").value.toString(),
                            chapter.child("hindiMLink").value.toString(),
                        )
                    )
                }
                _chapterList.postValue(chapterList)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}