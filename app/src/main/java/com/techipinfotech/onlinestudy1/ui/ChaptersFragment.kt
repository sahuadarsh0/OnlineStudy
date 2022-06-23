package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.techipinfotech.onlinestudy1.HomeApi
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.adapter.ChaptersAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentChaptersBinding
import com.techipinfotech.onlinestudy1.model.ChaptersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.SharedPrefs

class ChaptersFragment : Fragment() {


    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var binding: FragmentChaptersBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chapters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.subjectName =

        val args: ChaptersFragmentArgs by navArgs()
        userSharedPreferences = SharedPrefs(requireContext(), "USER")
        val subjects = args.subjects
        binding.subjectName.text = subjects.subjectName
        binding.subjectId.text = subjects.subjectId
        binding.chapters.layoutManager = LinearLayoutManager(context)
//        binding.chapters.adapter = object : ChaptersAdapter(context, subjects.chapters) {}

        getChapters(subjects.subjectId!!)

    }

    private fun getChapters(subjectId:String) {
        val getjsondata = HomeApi.getApiService().getSubjectJsonData(userSharedPreferences.get("student_id"),subjectId)
        getjsondata.enqueue(object : Callback<List<ChaptersItem?>?> {
            override fun onFailure(call: Call<List<ChaptersItem?>?>, t: Throwable) {
                Log.d("asa", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<ChaptersItem?>?>,
                response: Response<List<ChaptersItem?>?>
            ) {
                val classes = response.body()
//                viewModel.setJsonResponse(classes)

                binding.chapters.adapter = object : ChaptersAdapter(context, classes) {}

            }
        })

    }

}