package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techipinfotech.onlinestudy1.adapter.TopicsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var binding: FragmentTopicsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: TopicsFragmentArgs by navArgs()
        val chapters = args.chapters
        binding.chapterName.text = chapters.chapterName
        binding.chapterId.text = chapters.chapterId

        binding.topics.layoutManager = LinearLayoutManager(context)
        if (chapters.topics.isNullOrEmpty()) {
            binding.topics.visibility = View.GONE
            binding.noContent.visibility = View.VISIBLE
        } else {
            binding.topics.adapter = object : TopicsAdapter(context, chapters.topics) {}
        }
    }
}