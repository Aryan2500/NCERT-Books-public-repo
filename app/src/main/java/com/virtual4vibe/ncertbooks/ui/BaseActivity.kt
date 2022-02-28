package com.virtual4vibe.ncertbooks.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.virtual4vibe.ncertbooks.ui.viewModel.InternetViewModel
import com.virtual4vibe.ncertbooks.ui.viewModel.InternetViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
   lateinit var netViewModel: InternetViewModel

    override fun onResume() {
        val viewModeFact = InternetViewModelFactory(application)
        netViewModel = ViewModelProvider(this, viewModeFact)[InternetViewModel::class.java]
        super.onResume()
        try {
            if (netViewModel.hasInternetConnection()) {

            } else {
                Toast.makeText(this, "You have no active internet connection", Toast.LENGTH_LONG)
                    .show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}