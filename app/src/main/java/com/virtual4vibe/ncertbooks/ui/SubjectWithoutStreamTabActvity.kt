package com.virtual4vibe.ncertbooks.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.virtual4vibe.ncertbooks.databinding.ActivitySubjectWithoutStreamTabBinding
import com.virtual4vibe.ncertbooks.ui.adapter.SubjectAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.FirebaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectWithoutStreamTabActvity : BaseActivity() {

    private lateinit  var className: String
    private lateinit var cAdapter: SubjectAdapter
    val firebaseViewModel : FirebaseViewModel by viewModels()

    lateinit var bind: ActivitySubjectWithoutStreamTabBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubjectWithoutStreamTabBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val i = intent
        val topTitleOfClassActivity = i.getStringExtra("topTitleOfClassActivity")
        className = i.getStringExtra("className").toString()
        val mediumCode = i.getStringExtra("mediumCode").toString()

        val topTitle = "$topTitleOfClassActivity > $className"

        Log.v("streamcodet", "$mediumCode")
        bind.titleSubjectWithoutStreamActivity.text = topTitle
        cAdapter = SubjectAdapter(topTitle, className , mediumCode, this ,this)

        bind.subjItemRv.apply {
            adapter = cAdapter
            layoutManager = LinearLayoutManager(this@SubjectWithoutStreamTabActvity)
        }

        firebaseViewModel.fetchSubjects(className,"")
        firebaseViewModel.subjectList.observe(this ){
            if(it.isNotEmpty()){
                bind.spinSubject.visibility = View.GONE
                cAdapter.diff.submitList(it)
            }else{
                bind.spinSubject.visibility = View.GONE
                Toast.makeText(this , "Subject data not found" , Toast.LENGTH_LONG).show()
            }

        }
    }


}