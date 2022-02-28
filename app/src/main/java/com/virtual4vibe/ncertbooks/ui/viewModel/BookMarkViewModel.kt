package com.virtual4vibe.ncertbooks.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.virtual4vibe.ncertbooks.models.BookMark
import com.virtual4vibe.ncertbooks.source.local.NcertDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(dao: NcertDao) : ViewModel() {
    private val ncertDao = dao

    val bookmarkList = ncertDao.getbookMark().asLiveData()

    fun saveBookmark(bookMark: BookMark) {
        viewModelScope.launch(Dispatchers.IO) {
            ncertDao.insertBookmark(bookMark)
        }
    }

    fun deleteBookmark(bookMark: BookMark){
        viewModelScope.launch (Dispatchers.IO){
            ncertDao.deleteBookmark(bookMark)
        }
    }
}