package com.virtual4vibe.ncertbooks.ui

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.virtual4vibe.ncertbooks.common.DOC_STORAGE_PATH
import com.virtual4vibe.ncertbooks.databinding.ActivityDownloadBinding
import com.virtual4vibe.ncertbooks.models.Pdf
import com.virtual4vibe.ncertbooks.ui.viewModel.DownloadViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.Exception

@AndroidEntryPoint
class DownloadActivity : BaseActivity() {

    private var downloadId: Int? = null
    private val downlaodViewModel: DownloadViewModel by viewModels()
    lateinit var pdfFileName: String
    lateinit var downloadLink: String
    lateinit var subjectName: String
    lateinit var chapterName: String
    lateinit var bind: ActivityDownloadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDownloadBinding.inflate(layoutInflater)
        setContentView(bind.root)
        downloadLink = intent.getStringExtra("downloadLink").toString()
        pdfFileName = intent.getStringExtra("pdfFileName").toString()
        subjectName = intent.getStringExtra("subjectName").toString()
        chapterName = intent.getStringExtra("chapterName").toString()

        bind.topTitle.text = chapterName
        PRDownloader.initialize(applicationContext)



        checkPermission()
        bind.downloadFileBttn.setOnClickListener {

            donwloadFile()
            bind.downloadFileBttn.visibility = View.GONE

        }
        bind.openFileBttn.setOnClickListener {
            openFiile()
        }
    }

    private fun openFiile() {
        Intent(this@DownloadActivity, PdfViewActivity::class.java).apply {
            this.putExtra(
                "downloadedFileLocation",
                "${DOC_STORAGE_PATH}${File.separator}${pdfFileName}.pdf"
            )
            this.putExtra(
                "chapterName",
                chapterName
            )
            this.putExtra(
                "subjectName",
                subjectName
            )
            this.putExtra(
                "pdfFileName",
                pdfFileName
            )
            startActivity(this)
        }
        finish()
    }

    fun donwloadFile() {
//         val dirPath = filesDir.absolutePath+ File.separator+"NCERT Book & Solutions"
//        val downloadLink =
//            "https://firebasestorage.googleapis.com/v0/b/ncert-books-and-solution-3e038.appspot.com/o/books%2Fclass%202%2Fenglish%20medium%2Fmaths%2FClass2%20maths%20prelims.pdf?alt=media&token=a68eb717-5ebb-45a1-931f-586a316d62bf"
//        val pdfFileName = "2 engMedium Math-magic-II Prelims"
//        Log.v("data", "$downloadLink  filename  $pdfFileName  dirpath ${dir.path}")

        downloadId = PRDownloader.download(downloadLink, DOC_STORAGE_PATH, "$pdfFileName.pdf")
            .build()
            .setOnStartOrResumeListener {
                bind.progressPercentageTv.text = "Download started...  "
            }
            .setOnPauseListener { }
            .setOnCancelListener { }
            .setOnProgressListener {
                bind.hProgressBar.visibility = View.VISIBLE
                var progress = it.currentBytes * 100 / it.totalBytes
                bind.progressPercentageTv.text = "Downloaded... $progress %"
                bind.hProgressBar.progress = progress.toInt()
            }
            .start(object : OnDownloadListener {

                override fun onDownloadComplete() {
                    try {
                        downlaodViewModel.saveDownloadedPdf(
                            Pdf(
                                0,
                                "$subjectName   >>   $chapterName",
                                "$pdfFileName"
                            )
                        )
                    } catch (e: Exception) {
                        Log.v("roomError", "${e.message}")
                    }

                    openFiile()
                    bind.openFileBttn.visibility = View.VISIBLE


                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        this@DownloadActivity,
                        "Some Error Occurred in Downloading File\nTry Again Later",
                        Toast.LENGTH_LONG
                    ).show()
                    bind.downloadFileBttn.visibility = View.VISIBLE
                }
            })

        Log.v("downID", "$downloadId")
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
                            bind.downloadFileBttn.visibility = View.VISIBLE

                        }

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    bind.progressPercentageTv.text = "Please Grant the Permissoin to save the file"
                    token?.continuePermissionRequest()

                }
            }).check()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            PRDownloader.cancel(downloadId)
        } catch (e: Exception) {
            Toast.makeText(this, "Some Error Occurred Try Again Later", Toast.LENGTH_LONG).show()
        }
    }
}

