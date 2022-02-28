package com.virtual4vibe.ncertbooks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.virtual4vibe.ncertbooks.databinding.ActivityChapterBinding
import com.virtual4vibe.ncertbooks.source.remote.AppFirebase
import com.virtual4vibe.ncertbooks.ui.adapter.ChapterAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.DownloadViewModel
import com.virtual4vibe.ncertbooks.ui.viewModel.FirebaseViewModel


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChapterActivity : BaseActivity() {
    lateinit var bind: ActivityChapterBinding
    val firebaseViewModel : FirebaseViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityChapterBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val i = intent
        val topTitleOfSubjectActivity = i.getStringExtra("subjectAcitvityTitle")
        val subjectName = i.getStringExtra("subjectName").toString()
        val subjectId = i.getStringExtra("subject_id").toString()
        val mediumCode = i.getStringExtra("mediumCode").toString()
        val pdfFileHalfDetails = i.getStringExtra("pdfFileHalfDetail").toString()

        bind.titleChapterActivity.text = "$topTitleOfSubjectActivity > $subjectName"

        val cAdapter = ChapterAdapter(this, this ,pdfFileHalfDetails, subjectName, mediumCode)

        bind.chapterItemRv.apply {
            adapter = cAdapter
        }

        firebaseViewModel.fetchChapters(subjectId)
        firebaseViewModel.chapterList.observe(this ){
            if(it.isNotEmpty()){
                bind.spinChapter.visibility = View.GONE
                cAdapter.diff.submitList(it)
            }else{
                bind.spinChapter.visibility = View.GONE
                Toast.makeText(this , "chapters data not found" , Toast.LENGTH_LONG).show()
            }

        }
    }
}