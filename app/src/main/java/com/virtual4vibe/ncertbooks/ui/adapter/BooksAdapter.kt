package com.virtual4vibe.ncertbooks.ui.adapter

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.virtual4vibe.ncertbooks.common.slideDown
import com.virtual4vibe.ncertbooks.common.slideUp
import com.virtual4vibe.ncertbooks.databinding.ParentItemLayoutBinding
import com.virtual4vibe.ncertbooks.models.Category

class BooksAdapter(context: Context, val parentRV: RecyclerView  ) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    var c = context
    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    class BooksViewHolder(val bind: ParentItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
            ParentItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind.contentTitle.text = differ.currentList[position].cat_title
//        Log.v("list", "${differ.currentList[position]}")
        holder.itemView.setOnClickListener {
            var isOpen = differ.currentList[position].isOpen
            if (!isOpen) {
                slideDown(holder.bind.itemRV)
                TransitionManager.beginDelayedTransition(parentRV)
                holder.bind.itemRV.visibility = View.VISIBLE
                holder.bind.downArrow.visibility = View.GONE
                holder.bind.upArrow.visibility = View.VISIBLE
                differ.currentList[position].isOpen = true
                slideDown(holder.bind.itemRV )
            } else {
                slideUp(holder.bind.itemRV)
                TransitionManager.beginDelayedTransition(parentRV)
                holder.bind.itemRV.visibility = View.GONE
                holder.bind.downArrow.visibility = View.VISIBLE
                holder.bind.upArrow.visibility = View.GONE
                differ.currentList[position].isOpen = false
            }

            Log.v("isOpen3 $position", "$isOpen")
        }
        val nAdapter = NestedAdapter(c , differ.currentList[position].cat_title)

        nAdapter.nestedDiffer.submitList(differ.currentList[position].items)
        var lm: GridLayoutManager
        if (c.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lm = GridLayoutManager(c, 6)
        } else {
            lm = GridLayoutManager(c, 3)
        }

        holder.bind.itemRV.layoutManager = lm
        holder.bind.itemRV.adapter = nAdapter
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}