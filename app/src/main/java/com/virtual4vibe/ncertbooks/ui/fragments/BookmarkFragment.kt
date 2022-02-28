package com.virtual4vibe.ncertbooks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.databinding.FragmentBookmarkBinding
import com.virtual4vibe.ncertbooks.ui.adapter.BookmarkAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.BookMarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {
    lateinit var binding : FragmentBookmarkBinding
    private val bookMarkViewModel : BookMarkViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)

        val bookmarkAdapter = BookmarkAdapter(requireContext() , bookMarkViewModel)

        bookMarkViewModel.bookmarkList.observe(viewLifecycleOwner) { bookmarkList->
            if(bookmarkList.isEmpty()){
                binding.noBookmarkTv.visibility = View.VISIBLE
                binding.bookmarkRv.visibility = View.GONE
            }else{
                bookmarkAdapter.differ.submitList(bookmarkList)
                binding.noBookmarkTv.visibility = View.VISIBLE
                binding.noBookmarkTv.visibility = View.GONE
            }

        }

        binding.bookmarkRv.apply {
            adapter = bookmarkAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}