package com.virtual4vibe.ncertbooks.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InternetViewModelFactory(val app:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return InternetViewModel(app) as T
    }
}