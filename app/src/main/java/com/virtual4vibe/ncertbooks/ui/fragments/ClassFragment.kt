package com.virtual4vibe.ncertbooks.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.databinding.FragmentClassBinding
import com.virtual4vibe.ncertbooks.source.remote.AppFirebase
import com.virtual4vibe.ncertbooks.ui.adapter.ClassAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.FirebaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClassFragment : Fragment(R.layout.fragment_class) {

    val firebaseViewModel: FirebaseViewModel by viewModels()
    lateinit var bind: FragmentClassBinding
    var mediumCode = 0

    companion object {
        fun newInstance(args: Bundle): ClassFragment {
            val fragment = ClassFragment()
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentClassBinding.bind(view)
        if (arguments != null) {
            mediumCode = requireArguments().getInt("medium")
        }

        val topTitleOfClassActivity =
            requireActivity().findViewById<TextView>(R.id.classActivity_title).text
        val cAdapter = ClassAdapter(
            topTitleOfClassActivity.toString(),
            requireContext(),
            requireActivity(),
            mediumCode
        )

        bind.classItemRv.apply {
            adapter = cAdapter
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = GridLayoutManager(requireActivity(), 3)
            } else {
                layoutManager = GridLayoutManager(requireActivity(), 6)
            }

        }

        firebaseViewModel.fetchClassList()
        firebaseViewModel.classList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bind.spinClass.visibility = View.GONE
                cAdapter.differ.submitList(it)
            } else {
                bind.spinClass.visibility = View.GONE
                Toast.makeText(requireContext(), "Class Data not found", Toast.LENGTH_LONG).show()
            }

        }

    }
}
