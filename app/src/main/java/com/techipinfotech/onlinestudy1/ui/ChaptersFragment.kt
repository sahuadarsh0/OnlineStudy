package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.adapter.ChaptersAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentChaptersBinding

class ChaptersFragment : Fragment() {


    private lateinit var binding: FragmentChaptersBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.subjectName =

        val args: ChaptersFragmentArgs by navArgs()

        val subjects = args.subjects
        binding.subjectName.text = subjects.subjectName
        binding.subjectId.text = subjects.subjectId
        binding.chapters.layoutManager = LinearLayoutManager(context)
        binding.chapters.adapter = object : ChaptersAdapter(context, subjects.chapters) {}


    }

}