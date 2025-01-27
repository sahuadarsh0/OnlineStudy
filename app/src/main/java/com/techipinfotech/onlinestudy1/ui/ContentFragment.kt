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
import com.techipinfotech.onlinestudy1.adapter.ContentsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentContentBinding
import com.techipinfotech.onlinestudy1.model.ContentItem
import com.techipinfotech.onlinestudy1.utils.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContentFragment : Fragment() {

    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var binding: FragmentContentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ContentFragmentArgs by navArgs()

        userSharedPreferences = SharedPrefs(requireContext(), "USER")
        val topics = args.topics
        binding.topicName.text = topics.topicName
        binding.topicId.text = topics.topicId
        binding.contents.layoutManager = LinearLayoutManager(context)
        binding.progressBar.visibility = View.GONE
        getVideos(args.subjectId, args.chapterId, topics.topicId!!)

    }

    private fun getVideos(chapterId: String, subjectId: String, topicId: String) {
        binding.progressBar.visibility = View.VISIBLE
        val getJsonData = HomeApi.getApiService()
            .getVideo(subjectId, chapterId, topicId, userSharedPreferences.get("student_id"))

        getJsonData.enqueue(object : Callback<List<ContentItem>> {
            override fun onFailure(call: Call<List<ContentItem>>, t: Throwable) {
                Log.d("asa", "onFailure: " + t.message)
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<ContentItem>>,
                response: Response<List<ContentItem>>
            ) {
                val content = response.body()
                binding.progressBar.visibility = View.GONE
                if (content.isNullOrEmpty()) {
                    binding.contents.visibility = View.GONE
                    binding.noContent.visibility = View.VISIBLE
                } else
                    binding.contents.adapter = ContentsAdapter(context, content)

            }
        })
    }
}