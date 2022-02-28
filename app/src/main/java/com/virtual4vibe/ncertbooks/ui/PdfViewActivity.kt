package com.virtual4vibe.ncertbooks.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.databinding.ActivityPdfViewBinding
import com.virtual4vibe.ncertbooks.models.BookMark
import com.virtual4vibe.ncertbooks.ui.viewModel.BookMarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PdfViewActivity : BaseActivity() {
    lateinit var bookmarkTitle: String
    lateinit var bind: ActivityPdfViewBinding
    private val bookmarkViewModel: BookMarkViewModel by viewModels()
    lateinit var alertDialog: AlertDialog
    lateinit var subjectName: String
    lateinit var chapterName: String
    lateinit var pdfFileName: String
    lateinit var uri: Uri
    var bookmarkPageNumber = 0
    lateinit var title: String

    init {
        UnityInterStitialAds.loadAd("PdfViewerActivityBackPressAds")
    }

    var currentPageOnDialogShow = 0
    var currentPageOnSwitchClick = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val downloadedFileLocation = intent.getStringExtra("downloadedFileLocation").toString()

        pdfFileName = intent.getStringExtra("pdfFileName").toString()

        try {
            subjectName = intent.getStringExtra("subjectName").toString()
            chapterName = intent.getStringExtra("chapterName").toString()
            if (subjectName != "null" && chapterName != "null") {
                bookmarkTitle = "$subjectName  >> $chapterName"
            }
//            Toast.makeText(this@PdfViewActivity, "$subjectName $chapterName  ", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this@PdfViewActivity, "subject chapter ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
        try {
            bookmarkPageNumber = intent.getStringExtra("bookmarkPageNumber").toString().toInt()
        } catch (e: Exception) {
//            Toast.makeText(this@PdfViewActivity, "${e.message}", Toast.LENGTH_LONG).show()
        }

//        val downloadedFileLocation =  "/storage/emulated/0/Documents/2 engMedium Math-magic-II Prelims.pdf"
        try {
            title = intent.getStringExtra("title").toString()
            if (title != "null") {
                bookmarkTitle = "$title"
            }
        } catch (e: Exception) {
//            Toast.makeText(this@PdfViewActivity, "title ${e.message}", Toast.LENGTH_LONG).show()
        }
        uri = File(downloadedFileLocation).toUri()
        if (!File(downloadedFileLocation).exists()) {
            Toast.makeText(
                this@PdfViewActivity,
                "The Book has been deleted . Please Download Again",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }



        loadPdf(uri, false)

        bind.switchButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                loadPdf(uri, true)
            } else {
                loadPdf(uri, false)
            }
        }
    }

    private fun showDialog(bookmarkTitle: String, pageNumber: Int) {
        var dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.bookmark_dialogue_layout, null)
        dialogBuilder.setView(view)
        val yesButton = view.findViewById<Button>(R.id.buttonYes)
        val noButton = view.findViewById<Button>(R.id.buttonNo)
        val textview = view.findViewById<TextView>(R.id.dia_msg)
        yesButton.setOnClickListener {
            bookmarkViewModel.saveBookmark(
                BookMark(
                    0,
                    "$bookmarkTitle",
                    pageNumber,
                    pdfFileName
                )
            )
            Toast.makeText(this, "BookMark Saved", Toast.LENGTH_LONG).show()
            alertDialog.dismiss()
        }
        noButton.setOnClickListener {
            alertDialog.dismiss()
        }
        textview.text = "Do You Want to BookMark page $pageNumber ?"
        alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun getDefaultPage(): Int {
        return if (bookmarkPageNumber != 0) {
            bookmarkPageNumber - 1
        } else {
            0
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        UnityInterStitialAds.showAd("PdfViewerActivityBackPressAds", this)
        finish()
    }

    private fun loadPdf(uri: Uri, nightMode: Boolean) {
        var defaultPage = 0
        if (currentPageOnSwitchClick > 0) {
            defaultPage = currentPageOnSwitchClick
        } else {
            defaultPage = getDefaultPage()
        }
        bind.pdfView.fromUri(uri).defaultPage(defaultPage)
            .onPageChange { page, pageCount ->
                currentPageOnDialogShow = page + 1
                currentPageOnSwitchClick = page
            }
            .fitEachPage(true)
            .onLongPress {

                Toast.makeText(this@PdfViewActivity, "$currentPageOnDialogShow", Toast.LENGTH_LONG).show()
                showDialog(bookmarkTitle, currentPageOnDialogShow)

            }
            .nightMode(nightMode)
            .spacing(100)
            .scrollHandle(DefaultScrollHandle(this))
            .pageSnap(true).load()
    }

}