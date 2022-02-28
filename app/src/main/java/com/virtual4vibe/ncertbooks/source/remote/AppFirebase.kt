package com.virtual4vibe.ncertbooks.source.remote

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.virtual4vibe.ncertbooks.common.createClassList
import com.virtual4vibe.ncertbooks.common.fbRef
import com.virtual4vibe.ncertbooks.models.Category
import com.virtual4vibe.ncertbooks.models.CategoryItem
import com.virtual4vibe.ncertbooks.models.Chapter
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.models.Subject


class AppFirebase(val spin: SpinKitView) {

    fun getSubjectsList(classs: String, stream: String = "", diff: AsyncListDiffer<Subject>) {
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

                diff.submitList(subjectList)
                spin.visibility = View.GONE

            }

            override fun onCancelled(p0: DatabaseError) = Unit

        })
    }

    fun getTextBookChapters(subject_id: String, diff: AsyncListDiffer<Chapter>) {
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

                diff.submitList(chapterList)
                spin.visibility = View.GONE
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getClassList(diff: AsyncListDiffer<Class>) {
        val db = fbRef().child("categories").child("1").child("items").child("1")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                var classString = ""

                classString = ds.child("classes").value.toString()

                Log.v("classString", "${ds.child("classes")}")

                diff.submitList(createClassList(classString.split(",")))
                spin.visibility = View.GONE
            }

            override fun onCancelled(p0: DatabaseError) {
                spin.visibility = View.GONE
            }

        })
    }
}