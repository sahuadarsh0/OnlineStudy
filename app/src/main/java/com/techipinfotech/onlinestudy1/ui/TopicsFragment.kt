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
import com.techipinfotech.onlinestudy1.adapter.TopicsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var binding: FragmentTopicsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topics, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.topicName.text =


        val args: TopicsFragmentArgs by navArgs()

        val chapters = args.chapters
        binding.chapterName.text = chapters.chapterName
        binding.chapterId.text = chapters.chapterId

        binding.topics.layoutManager = LinearLayoutManager(context)
        binding.topics.adapter = object : TopicsAdapter(context, chapters.topics) {}

    }
}