package com.virtual4vibe.ncertbooks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.virtual4vibe.ncertbooks.R
import com.virtual4vibe.ncertbooks.databinding.FragmentSubjectsBinding
import com.virtual4vibe.ncertbooks.models.Subject
import com.virtual4vibe.ncertbooks.source.remote.AppFirebase
import com.virtual4vibe.ncertbooks.ui.adapter.SubjectAdapter
import com.virtual4vibe.ncertbooks.ui.viewModel.FirebaseViewModel
import com.virtual4vibe.ncertbooks.ui.viewModel.InternetViewModel
import com.virtual4vibe.ncertbooks.ui.viewModel.InternetViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectFragment : Fragment(R.layout.fragment_subjects) {
    lateinit var bind: FragmentSubjectsBinding
    lateinit var title: String
    lateinit var className: String
    lateinit var streamCode: String
    lateinit var mediumCode: String
    val firebaseViewModel: FirebaseViewModel by viewModels()

    companion object {
        fun newInstance(args: Bundle): SubjectFragment {
            val fragment = SubjectFragment()
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentSubjectsBinding.bind(view)

        if (arguments != null) {
            className = requireArguments().getString("className").toString()
            mediumCode = requireArguments().getString("medium").toString()
            streamCode = requireArguments().getString("streamCode").toString()
            title = requireArguments().getString("topTitle").toString()
        }

        val cAdapter =
            SubjectAdapter(title, className, mediumCode, requireActivity(), requireContext())

        var list: ArrayList<Subject> = ArrayList()

        firebaseViewModel.fetchSubjects(className, streamCode)
        firebaseViewModel.subjectList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bind.spinStreaWisesubject.visibility = View.GONE
                cAdapter.diff.submitList(it)
            } else {
                bind.spinStreaWisesubject.visibility = View.GONE
                Toast.makeText(requireContext(), "Subject data not found", Toast.LENGTH_LONG).show()
            }
        }
        cAdapter.diff.submitList(list)
        bind.subjectItemRv.apply {
            adapter = cAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }
    }
}
