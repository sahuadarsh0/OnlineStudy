package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techipinfotech.onlinestudy1.HomeApi
import com.techipinfotech.onlinestudy1.adapter.TopicsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTopicsBinding
import com.techipinfotech.onlinestudy1.model.TopicsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        binding.topics.layoutManager = LinearLayoutManager(context)
        binding.progressBar.visibility = View.GONE
        getTopics(chapters.chapterId!!, args.subjectId)

    }

    private fun getTopics(chapterId: String, subjectId: String) {
        binding.progressBar.visibility = View.VISIBLE
        val getJsonData = HomeApi.getApiService().getTopicJsonData(subjectId, chapterId)

        getJsonData.enqueue(object : Callback<List<TopicsItem>> {
            override fun onFailure(call: Call<List<TopicsItem>>, t: Throwable) {
                Log.d("asa", "onFailure: " + t.message)
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<TopicsItem>>,
                response: Response<List<TopicsItem>>
            ) {
                val topics = response.body()
                binding.progressBar.visibility = View.GONE
                if (topics.isNullOrEmpty()) {
                    binding.topics.visibility = View.GONE
                    binding.noContent.visibility = View.VISIBLE
                } else {
                    binding.topics.adapter = TopicsAdapter(topics,chapterId,subjectId)
                }
            }
        })
    }
}