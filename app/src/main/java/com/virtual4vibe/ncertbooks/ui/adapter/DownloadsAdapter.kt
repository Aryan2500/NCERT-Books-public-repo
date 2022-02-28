package com.virtual4vibe.ncertbooks.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.virtual4vibe.ncertbooks.common.DOC_STORAGE_PATH
import com.virtual4vibe.ncertbooks.common.getRandomColor
import com.virtual4vibe.ncertbooks.databinding.DownloadsItemBinding
import com.virtual4vibe.ncertbooks.models.Pdf
import com.virtual4vibe.ncertbooks.ui.PdfViewActivity
import com.virtual4vibe.ncertbooks.ui.viewModel.DownloadViewModel
import java.io.File

class DownloadsAdapter(val c: Context, val downloadViewModel: DownloadViewModel) :
    RecyclerView.Adapter<DownloadsAdapter.DownloadsViewHolder>() {
    var nameList = ArrayList<String>()
    init {
        val dir = File(DOC_STORAGE_PATH)
        if(!dir.exists()){
            dir.mkdir()
        }
        val files = dir.listFiles()
        if(!files.isNullOrEmpty()){
            for (file in files) {
                nameList.add(file.name)
            }
        }

    }

    class DownloadsViewHolder(val binding: DownloadsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Pdf>() {
        override fun areItemsTheSame(oldItem: Pdf, newItem: Pdf): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Pdf, newItem: Pdf): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsViewHolder {
        return DownloadsViewHolder(
            DownloadsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DownloadsViewHolder, position: Int) {
        holder.binding.fileNameTv.text = differ.currentList[holder.adapterPosition].title
        val file =
            File(DOC_STORAGE_PATH + File.separator, differ.currentList[holder.adapterPosition].pdfname + ".pdf")
        holder.binding.fileNameTv.setTextColor(getRandomColor())
        holder.binding.deleteImgv.setOnClickListener {

            try {
                file.delete()

            } catch (e: Exception) {
                Log.v("errorDelete", "${e.message}")
            }
            try {
                downloadViewModel.deleteDownloadedPdf(differ.currentList[holder.adapterPosition])
            } catch (e: Exception) {
                Log.v("errorDelete", "${differ.currentList[holder.adapterPosition]}")
            }
        }

        if ( ! nameList.contains( differ.currentList[holder.adapterPosition].pdfname+".pdf")  ) {
            try {
                downloadViewModel.deletePdfsWithId(differ.currentList[holder.adapterPosition].id)

                notifyItemRemoved(holder.adapterPosition)
                notifyItemRangeChanged(holder.adapterPosition , itemCount)
            }catch (e:Exception){

            }

            Log.v("roompdfName" ,differ.currentList[position].pdfname)
            Log.v("filepdfName" , nameList.toString())


        }

        holder.binding.fileNameContainer.setOnClickListener {
            var downloadedFileLocation = ""
            try {
                  downloadedFileLocation =
                    "${DOC_STORAGE_PATH}${File.separator}${differ.currentList[holder.adapterPosition].pdfname}.pdf"
            }catch (e:Exception){
                Log.v("exception" , "${e.message}")
            }


            Intent(c, PdfViewActivity::class.java).apply {
                this.putExtra(
                    "downloadedFileLocation",
                    downloadedFileLocation
                )
                this.putExtra(
                    "title",
                    differ.currentList[holder.adapterPosition].title
                )
                this.putExtra(
                    "pdfFileName",
                    differ.currentList[holder.adapterPosition].pdfname
                )
                c.startActivity(this)
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}