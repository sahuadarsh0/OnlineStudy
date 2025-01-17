package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.techipinfotech.onlinestudy1.MainActivityViewModel
import com.techipinfotech.onlinestudy1.adapter.SubjectsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentSubjectsBinding

class SubjectsFragment : Fragment() {


    private lateinit var binding: FragmentSubjectsBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.subjects.layoutManager = GridLayoutManager(context, 2)
        viewModel.jsonResponse.observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty()) {
                binding.subjects.visibility = View.GONE
                binding.noContent.visibility = View.VISIBLE
            } else {
                binding.subjects.adapter =
                    SubjectsAdapter(context, viewModel.jsonResponse.value?.get(0)?.subjects)
                binding.className.text = viewModel.jsonResponse.value?.get(0)?.className
            }
        }

    }

}

