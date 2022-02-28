package com.virtual4vibe.ncertbooks.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtual4vibe.ncertbooks.models.Chapter
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.models.Subject
import com.virtual4vibe.ncertbooks.source.remote.FirebaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {

    val firebaseRepo = FirebaseRepo()

    private val _classList = MutableLiveData<List<Class>>()
    val classList: MutableLiveData<List<Class>> = _classList

    private val _subjectList = MutableLiveData<List<Subject>>()
    val subjectList: MutableLiveData<List<Subject>> = _subjectList

    private val _chapterList = MutableLiveData<List<Chapter>>()
    val chapterList: MutableLiveData<List<Chapter>> = _chapterList

    fun fetchClassList() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepo.getClassList(_classList)
        }
    }

    fun fetchSubjects(classs: String, stream: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepo.getSubjectsList(classs, stream, _subjectList)
        }
    }

    fun fetchChapters(subjectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepo.getTextBookChapters(subjectId ,_chapterList)
        }
    }

}