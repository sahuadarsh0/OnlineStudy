package com.techipinfotech.onlinestudy1.test

import android.os.Bundle
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
import com.techipinfotech.onlinestudy1.adapter.TestSeriesAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTestHomeBinding


class TestHomeFragment : Fragment() {

    private lateinit var binding: FragmentTestHomeBinding
    private lateinit var viewModel: OnlineTestActivityViewModel
    private lateinit var animation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_home, container, false)
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

        binding.recyclerTestSeries.layoutManager = GridLayoutManager(context, 3)
        viewModel.testResponse.removeObservers(viewLifecycleOwner);
        viewModel.testResponse.observe(viewLifecycleOwner, {

            if (!it?.isEmpty()!!) {

                binding.recyclerTestSeries.adapter =
                    TestSeriesAdapter(context, viewModel.testResponse.value?.get(0)?.testSeries,1)

            }
        })


    }


}
