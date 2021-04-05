package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.techipinfotech.onlinestudy1.MainActivityViewModel
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.adapter.SubjectsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentSubjectsBinding

class SubjectsFragment : Fragment() {


    private lateinit var binding: FragmentSubjectsBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subjects, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.subjects.layoutManager = GridLayoutManager(context, 2)
        viewModel.jsonResponse.observe(viewLifecycleOwner, Observer {

            if (!it?.isEmpty()!!) {
                binding.subjects.adapter = SubjectsAdapter(context, viewModel.jsonResponse.value?.get(0)?.subjects)
                binding.className.text = viewModel.jsonResponse.value?.get(0)?.className
            }
        })

    }

}

