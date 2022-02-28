package com.virtual4vibe.ncertbooks.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.common.getRandomColor
import com.virtual4vibe.ncertbooks.databinding.SubjectItemLayoutBinding
import com.virtual4vibe.ncertbooks.models.Subject
import com.virtual4vibe.ncertbooks.ui.ChapterActivity

class SubjectAdapter(val title: String, val className: String, val mediumCode: String, val a:Activity , val c: Context) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    init {
        UnityInterStitialAds.loadAd("SubjectClickAds")
    }

    class SubjectViewHolder(val bind: SubjectItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.subjectNumber == newItem.subjectNumber
        }

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem == newItem
        }

    }

    val diff = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder(
            SubjectItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        Log.v("streamcodeAdapter", mediumCode)
        Log.v("streamcodeAdapter", diff.currentList[position].subjectNameInHin)
        var subjectName = ""
        var medium = ""

        if(mediumCode=="0"){
            holder.bind.subjectName.text = diff.currentList[position].subjectNameInEng
            subjectName = diff.currentList[position].subjectNameInEng
            medium = "engMedium"
        }else{
            holder.bind.subjectName.text = diff.currentList[position].subjectNameInHin
            subjectName = diff.currentList[position].subjectNameInHin
            medium = "hindiMedium"
        }
        var pdfFileHalfDetails = "$className $medium $subjectName"
        holder.bind.subjectNumber.text = diff.currentList[position].subjectNumber
        val randColor = getRandomColor()
        holder.bind.subjectNumber.setBackgroundColor(randColor)
        holder.bind.subjectName.setTextColor(randColor)

        holder.itemView.setOnClickListener {

            UnityInterStitialAds.showAd("SubjectClickAds" , a)

            Intent(c, ChapterActivity::class.java).apply {
                this.putExtra("subjectAcitvityTitle" , title)
                this.putExtra("mediumCode" , mediumCode)
                this.putExtra("subject_id" , diff.currentList[position].subject_id)
                this.putExtra("subjectName" ,  subjectName)
                this.putExtra("pdfFileHalfDetail" ,  pdfFileHalfDetails)
                c.startActivity(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }
}