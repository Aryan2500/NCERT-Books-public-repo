package com.virtual4vibe.ncertbooks.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.virtual4vibe.ncertbooks.models.Pdf
import com.virtual4vibe.ncertbooks.source.local.NcertDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel  @Inject constructor(dao:NcertDao) : ViewModel() {
    private val ncertDao = dao

    val downloadPdfList = ncertDao.getDownloadedPdf().asLiveData()

    fun saveDownloadedPdf( downloadedPdf : Pdf){
        viewModelScope.launch(Dispatchers.IO) {
            ncertDao.insertDownloadedPdf(downloadedPdf)
        }
    }

    fun deleteDownloadedPdf(downloadedPdf: Pdf){
        viewModelScope.launch(Dispatchers.IO) {
            ncertDao.deletePdf(downloadedPdf)
        }
    }

    fun deletePdfsWithId(id:Int ){
        viewModelScope.launch (Dispatchers.IO){
            ncertDao.deletePdfWithId(id)
        }
    }
}