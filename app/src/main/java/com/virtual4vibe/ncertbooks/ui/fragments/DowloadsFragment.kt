package com.virtual4vibe.ncertbooks.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.databinding.FragmentDowloadsBinding
import com.virtual4vibe.ncertbooks.ui.adapter.DownloadsAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.DownloadViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DowloadsFragment : Fragment(R.layout.fragment_dowloads) {

    private val downloadViewModel: DownloadViewModel by viewModels()

    lateinit var binding: FragmentDowloadsBinding
    lateinit var dAdapter: DownloadsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDowloadsBinding.bind(view)
        dAdapter = DownloadsAdapter(requireContext(), downloadViewModel)

        binding.downloadedRv.apply {
            adapter = dAdapter
        }

        downloadViewModel.downloadPdfList.observe(viewLifecycleOwner) { PdfList ->

            if (PdfList.isEmpty()) {
                binding.noDataTv.visibility = View.VISIBLE
                binding.downloadedRv.visibility = View.GONE
            } else {
                dAdapter.differ.submitList(PdfList)
                dAdapter
                binding.downloadedRv.visibility = View.VISIBLE
                binding.noDataTv.visibility = View.GONE
            }


        }
    }


}