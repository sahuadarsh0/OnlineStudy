package com.techipinfotech.onlinestudy1.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.techipinfotech.onlinestudy1.OnlineTestActivityViewModel
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.TestApi
import com.techipinfotech.onlinestudy1.adapter.TestSeriesAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTestResultBinding
import com.techipinfotech.onlinestudy1.model.TestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.SharedPrefs


class TestResultFragment : Fragment() {

    private lateinit var binding: FragmentTestResultBinding
    private lateinit var viewModel: OnlineTestActivityViewModel
    private lateinit var animation: Animation
    private lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_result, container, false)
        viewModel =
            ViewModelProvider(requireActivity()).get(OnlineTestActivityViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
//        binding.video.startAnimation(animation)
//        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
//        binding.test.startAnimation(animation)

        userSharedPreferences = SharedPrefs(context, "USER")
        binding.recyclerTestSeries.layoutManager = GridLayoutManager(context, 2)
        viewModel.testResultResponse.removeObservers(viewLifecycleOwner);
        viewModel.testResultResponse.observe(viewLifecycleOwner, {

            if (!it?.isEmpty()!!) {

                binding.recyclerTestSeries.adapter =
                    TestSeriesAdapter(
                        context,
                        viewModel.testResultResponse.value?.get(0)?.testSeries,
                        2
                    )
            }
        })
        getTestResult()

    }

    private fun getTestResult() {

        val getTestResult = TestApi.getApiService().gettestresult(userSharedPreferences.get("student_mobile"))
        getTestResult.enqueue(object : Callback<List<TestResponse?>?> {
            override fun onFailure(call: Call<List<TestResponse?>?>, t: Throwable) {
                Log.d("asa", "onFailure: " + t.message)
            }

            override fun onResponse(
                call: Call<List<TestResponse?>?>,
                response: Response<List<TestResponse?>?>
            ) {
                val result = response.body()
                viewModel.setTestResultResponse(result)


            }
        })
    }


}
