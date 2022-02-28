package com.virtual4vibe.ncertbooks.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.virtual4vibe.ncertbooks.databinding.ChildItemLayoutBinding
import com.virtual4vibe.ncertbooks.models.CategoryItem
import com.virtual4vibe.ncertbooks.ui.ClassActivity
import java.io.Serializable
import java.lang.Exception

class NestedAdapter(val c: Context, val categoryName: String) : RecyclerView.Adapter<NestedAdapter.NestedViewHolder>() {

    class NestedViewHolder(val binding: ChildItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differ = object : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.item_title == newItem.item_title
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

    }
    val nestedDiffer = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedViewHolder {
        return NestedViewHolder(
            ChildItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NestedViewHolder, position: Int) {
//        Log.v("list", "${nestedDiffer.currentList[position].}")
        if(nestedDiffer.currentList[position]!=null){
            holder.binding.itemTitle.text = nestedDiffer.currentList[position].item_title
            Glide.with(c).load(nestedDiffer.currentList[position].icon).into(holder.binding.bookIcon)

            holder.itemView.setOnClickListener {
//                Log.v("classList" , "${nestedDiffer.currentList[position].classsList}")
                try {
                    Intent(c, ClassActivity::class.java).apply {
                        this.putExtra("categoryName" , categoryName)
                        this.putExtra("itemTitle" , nestedDiffer.currentList[position].item_title)
                        this.putExtra("classList" , nestedDiffer.currentList[position].classsList as Serializable)
                        c.startActivity(this)
                    }
                }catch (e:Exception){
                    Log.d("error-->" ,"${e.message}")
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return nestedDiffer.currentList.size
    }
}