package com.virtual4vibe.ncertbooks.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.virtual4vibe.ncertbooks.common.DOC_STORAGE_PATH
import com.virtual4vibe.ncertbooks.common.getRandomColor
import com.virtual4vibe.ncertbooks.databinding.DownloadsItemBinding
import com.virtual4vibe.ncertbooks.models.BookMark
import com.virtual4vibe.ncertbooks.ui.PdfViewActivity
import com.virtual4vibe.ncertbooks.ui.viewModel.BookMarkViewModel
import java.io.File


class BookmarkAdapter(val c: Context, val bookmarkViewModel: BookMarkViewModel) :
    RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(val binding: DownloadsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<BookMark>() {
        override fun areItemsTheSame(oldItem: BookMark, newItem: BookMark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookMark, newItem: BookMark): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            DownloadsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.binding.fileNameTv.text = "${ differ.currentList[position].title } >> Page ${differ.currentList[position].pageNumber}"

        holder.binding.fileNameTv.setTextColor(getRandomColor())
        holder.binding.deleteImgv.setOnClickListener {

            try {
                bookmarkViewModel.deleteBookmark(differ.currentList[position])
            } catch (e: Exception) {
                Log.v("errorDelete", "${e.message}")
            }
        }
        holder.binding.fileNameContainer.setOnClickListener {
            val downloadedFileLocation = "$DOC_STORAGE_PATH${File.separator}${differ.currentList[position].file_name}.pdf"
            if (!File(downloadedFileLocation).exists()) {
                Toast.makeText(
                    c,
                    "The Book has been deleted . Please Download Again",
                    Toast.LENGTH_LONG
                ).show()
                bookmarkViewModel.deleteBookmark(differ.currentList[position])
            }else{
                Intent(c, PdfViewActivity::class.java).apply {
                    this.putExtra(
                        "downloadedFileLocation",
                        downloadedFileLocation
                    )
                    this.putExtra(
                        "bookmarkPageNumber",
                        differ.currentList[position].pageNumber.toString()
                    )
                    this.putExtra(
                        "title",
                        differ.currentList[position].title
                    )
                    this.putExtra(
                        "pdfFileName",
                        differ.currentList[position].file_name
                    )

                    c.startActivity(this)
                }
            }


        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}