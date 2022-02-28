package com.virtual4vibe.ncertbooks.ui.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.common.DOC_STORAGE_PATH
import com.virtual4vibe.ncertbooks.common.FileExist
import com.virtual4vibe.ncertbooks.common.getRandomColor
import com.virtual4vibe.ncertbooks.databinding.ChapterItemLayoutBinding
import com.virtual4vibe.ncertbooks.models.Chapter
import com.virtual4vibe.ncertbooks.ui.DownloadActivity
import com.virtual4vibe.ncertbooks.ui.PdfViewActivity
import com.virtual4vibe.ncertbooks.ui.WebViewActivity
import java.io.File

class ChapterAdapter(
    val c: Context,
    val a: Activity,
    val pdfFileHalfDetails: String,
    val subjectName: String,
    val mediumCode: String
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    init {
        UnityInterStitialAds.loadAd("ChapterClickAds")
    }

    class ChapterViewHolder(val bind: ChapterItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Chapter>() {


        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.nameInEng == newItem.nameInEng
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }

    }

    val diff = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
            ChapterItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        var downloadLink: String
        var chapterName: String
        if (mediumCode == "0") {
            holder.bind.chapterName.text = diff.currentList[position].nameInEng
            chapterName = diff.currentList[position].nameInEng
            downloadLink = diff.currentList[position].engMlink
        } else {
            holder.bind.chapterName.text = diff.currentList[position].nameInHindi
            chapterName = diff.currentList[position].nameInHindi
            downloadLink = diff.currentList[position].hindiMLink
        }
        val pdfFileFullDetails = "$pdfFileHalfDetails $chapterName"
        holder.bind.chapterNumber.text = diff.currentList[position].chapterNumber

        val randColor = getRandomColor()
        holder.bind.chapterNumber.setBackgroundColor(randColor)
        holder.bind.chapterName.setTextColor(randColor)

        val uri = FileExist.check(pdfFileFullDetails)
        if (uri is Uri) {
            holder.bind.openFile.visibility = View.VISIBLE
            holder.bind.deleteFile.visibility = View.VISIBLE
            holder.bind.viewOnline.visibility = View.GONE
            holder.bind.viewOffline.visibility = View.GONE
        } else {
            holder.bind.openFile.visibility = View.GONE
            holder.bind.deleteFile.visibility = View.GONE
            holder.bind.viewOnline.visibility = View.VISIBLE
            holder.bind.viewOffline.visibility = View.VISIBLE
        }

        holder.bind.viewOnline.setOnClickListener {
            Intent(c, WebViewActivity::class.java).apply {
                this.putExtra("downloadLink", downloadLink)
                c.startActivity(this)
            }

            UnityInterStitialAds.showAd("ChapterClickAds" ,a)
        }
        holder.bind.viewOffline.setOnClickListener {
            Intent(c, DownloadActivity::class.java).apply {
                this.putExtra("downloadLink", downloadLink)
                this.putExtra("pdfFileName", pdfFileFullDetails)
                this.putExtra("chapterName", chapterName)
                this.putExtra("subjectName", subjectName)
                c.startActivity(this)
            }
            UnityInterStitialAds.showAd("ChapterClickAds" ,a)
        }

        holder.bind.openFile.setOnClickListener {
            checkPermission(pdfFileFullDetails , chapterName)
        }
        val file =
            File("${DOC_STORAGE_PATH}${File.separator}$pdfFileFullDetails.pdf")
        holder.bind.deleteFile.setOnClickListener {
            try {
                file.delete()
            } catch (e: Exception) {
                Log.v("errorDelete", "${e.message}")
            }
            notifyItemChanged(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    private fun checkPermission(pdfFileFullDetails :String , chapterName:String , ) {
        Dexter.withContext(c)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            Intent(c, PdfViewActivity::class.java).apply {
                                this.putExtra(
                                    "downloadedFileLocation",
                                    "${DOC_STORAGE_PATH}${File.separator}$pdfFileFullDetails.pdf"
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
                                    "$pdfFileFullDetails.pdf"
                                )
                                c.startActivity(this)
                            }

                            UnityInterStitialAds.showAd("ChapterClickAds" ,a)

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
}