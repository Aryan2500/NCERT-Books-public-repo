package com.virtual4vibe.ncertbooks.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.unity3d.ads.UnityAds
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.common.TEST_MODE
import com.virtual4vibe.ncertbooks.common.UNITY_GAME_ID
import com.virtual4vibe.ncertbooks.databinding.ActivityPdfReaderForOutsideBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PdfReaderForOutsideActivity : AppCompatActivity() {
    lateinit var bind: ActivityPdfReaderForOutsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader_for_outside)
        bind = ActivityPdfReaderForOutsideBinding.inflate(layoutInflater)

        setContentView(bind.root)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                //unity ads initialization
                UnityAds.initialize(
                    this@PdfReaderForOutsideActivity,
                    UNITY_GAME_ID,
                    null,
                    TEST_MODE
                )
            } catch (e: Exception) {
            }
        }


        UnityInterStitialAds.loadAd("PdfReaderForOutsideActivityAds")

        checkPermission()


    }

    private fun checkPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            try {
                                val filePath = intent.data
                                bind.outSidePdfView.fromUri(filePath)
                                    .scrollHandle(DefaultScrollHandle(this@PdfReaderForOutsideActivity))
                                    .load()
                            } catch (e: Exception) {
                                Toast.makeText(this@PdfReaderForOutsideActivity  , "Unable read file" , Toast.LENGTH_LONG).show()

                            }

                        }

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {

                    token?.continuePermissionRequest()

                }
            }).check()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        UnityInterStitialAds.showAd("PdfReaderForOutsideActivityAds", this)
    }


}