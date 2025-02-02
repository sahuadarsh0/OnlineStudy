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
import com.techipinfotech.onlinestudy1.adapter.ChaptersAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentChaptersBinding
import com.techipinfotech.onlinestudy1.model.ChaptersItem
import com.techipinfotech.onlinestudy1.utils.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChaptersFragment : Fragment() {

    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var binding: FragmentChaptersBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChaptersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSharedPreferences = SharedPrefs(requireContext(), "USER")
        val args: ChaptersFragmentArgs by navArgs()
        val subjects = args.subjects
        binding.subjectName.text = subjects.subjectName
        binding.subjectId.text = subjects.subjectId
        binding.chapters.layoutManager = LinearLayoutManager(context)
        binding.progressBar.visibility = View.GONE
        getChapters(subjects.subjectId!!)

    }

    private fun getChapters(subjectId: String) {
        binding.progressBar.visibility = View.VISIBLE
        val getJsonData = HomeApi.getApiService().getChapterJsonData(subjectId, userSharedPreferences.get("student_id"))
        getJsonData.enqueue(object : Callback<List<ChaptersItem>?> {
            override fun onFailure(call: Call<List<ChaptersItem>?>, t: Throwable) {
                Log.d("asa", "onFailure: " + t.message)
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<ChaptersItem>?>,
                response: Response<List<ChaptersItem>?>
            ) {
                val classes = response.body()

                binding.progressBar.visibility = View.GONE
                if (classes.isNullOrEmpty()) {
                    binding.chapters.visibility = View.GONE
                    binding.noContent.visibility = View.VISIBLE
                } else {
                    binding.chapters.adapter = ChaptersAdapter(classes, subjectId)
                }
            }
        })

    }

}