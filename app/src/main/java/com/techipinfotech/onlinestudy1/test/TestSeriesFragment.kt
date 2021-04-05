package com.techipinfotech.onlinestudy1.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.techipinfotech.onlinestudy1.API
import com.techipinfotech.onlinestudy1.OnlineTestActivityViewModel
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.adapter.ExamsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTestSeriesBinding
import technited.minds.androidutils.SharedPrefs


class TestSeriesFragment : Fragment() {

    private lateinit var binding: FragmentTestSeriesBinding
    private lateinit var viewModel: OnlineTestActivityViewModel
    private lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_series, container, false)
        viewModel =
            ViewModelProvider(requireActivity()).get(OnlineTestActivityViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userSharedPreferences = SharedPrefs(context, "USER")

        val args: TestSeriesFragmentArgs by navArgs()

        val testSeries = args.testSeries
        val url = API.TEST.toString() + testSeries.testImage

        binding.apply {
            Glide
                .with(requireContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_triangle)
                .into(testSeriesImage)
            testSeriesName.text = testSeries.testName
            testDescription.text = testSeries.description
            recyclerExams.layoutManager =  LinearLayoutManager(context)
            recyclerExams.adapter = object : ExamsAdapter(context, testSeries.exams) {}

        }



    }


}
