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
import com.techipinfotech.onlinestudy1.adapter.ContentsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentContentBinding


class ContentFragment : Fragment() {


    private lateinit var binding: FragmentContentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.subjectName =

        val args: ContentFragmentArgs by navArgs()

        val topics = args.topics
        binding.topicName.text = topics.topicName
        binding.topicId.text = topics.topicId
        binding.contents.layoutManager = LinearLayoutManager(context)
        binding.contents.adapter = object : ContentsAdapter(context, topics.content) {}


    }

}