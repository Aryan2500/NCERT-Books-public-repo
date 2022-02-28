package com.virtual4vibe.ncertbooks.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.virtual4vibe.ncertbooks.ads.UnityInterStitialAds
import com.virtual4vibe.ncertbooks.common.getRandomColor
import com.virtual4vibe.ncertbooks.databinding.ClassItemBinding
import com.virtual4vibe.ncertbooks.models.Class
import com.virtual4vibe.ncertbooks.ui.SubjectActvity
import com.virtual4vibe.ncertbooks.ui.SubjectWithoutStreamTabActvity

class ClassAdapter(
    val topTitleOfClassActivity: String,
    val c: Context,
    val a : Activity,
    val mediumCode: Int
) : RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    init {
        UnityInterStitialAds.loadAd("ClassClickAds")
    }

    class ClassViewHolder(val bind: ClassItemBinding) : RecyclerView.ViewHolder(bind.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Class>() {
        override fun areItemsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem.className == newItem.className
        }

        override fun areContentsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(
            ClassItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {

        if (mediumCode == 0) {
            holder.bind.classTitle.text = "Class ${differ.currentList[position].className}"
        } else {
            holder.bind.classTitle.text = "कक्षा  ${differ.currentList[position].className}"
        }
        val randColor = getRandomColor()
        holder.bind.classSymbol.setBackgroundColor(randColor)
        holder.bind.classTitle.setTextColor(randColor)

        when (differ.currentList[position].className) {
            "1" -> holder.bind.classSymbol.text = "I"
            "2" -> holder.bind.classSymbol.text = "II"
            "3" -> holder.bind.classSymbol.text = "III"
            "4" -> holder.bind.classSymbol.text = "IV"
            "5" -> holder.bind.classSymbol.text = "V"
            "6" -> holder.bind.classSymbol.text = "VI"
            "7" -> holder.bind.classSymbol.text = "VII"
            "8" -> holder.bind.classSymbol.text = "VIII"
            "9" -> holder.bind.classSymbol.text = "IX"
            "10" -> holder.bind.classSymbol.text = "X"
            "11" -> holder.bind.classSymbol.text = "XI"
            "12" -> holder.bind.classSymbol.text = "XII"
        }

        holder.itemView.setOnClickListener {
            if (differ.currentList[position].className.toInt() <= 10) {
                Intent(c, SubjectWithoutStreamTabActvity::class.java).apply {
                    this.putExtra("topTitleOfClassActivity", topTitleOfClassActivity)
                    this.putExtra("className", differ.currentList[position].className)
                    this.putExtra("mediumCode", mediumCode.toString())
                    c.startActivity(this)
                }
            } else {
                Intent(c, SubjectActvity::class.java).apply {
                    this.putExtra("topTitleOfClassActivity", topTitleOfClassActivity)
                    this.putExtra("className", differ.currentList[position].className)
                    this.putExtra("mediumCode", mediumCode.toString())
                    c.startActivity(this)
                }
            }

            UnityInterStitialAds.showAd("ClassClickAds", a)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}