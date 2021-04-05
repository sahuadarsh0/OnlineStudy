package com.techipinfotech.onlinestudy1.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.techipinfotech.onlinestudy1.OnlineTestActivity
import com.techipinfotech.onlinestudy1.OnlineTestActivityViewModel
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.adapter.InstructionsAdapter
import com.techipinfotech.onlinestudy1.databinding.FragmentTestInfoBinding
import technited.minds.androidutils.SharedPrefs


class TestInfoFragment : Fragment() {

    private lateinit var binding: FragmentTestInfoBinding
    private lateinit var viewModel: OnlineTestActivityViewModel
    private lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_info, container, false)
        viewModel = (activity as OnlineTestActivity).viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userSharedPreferences = SharedPrefs(context, "USER")
        binding.recyclerInstructions.layoutManager = LinearLayoutManager(context)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.testResponse.removeObservers(viewLifecycleOwner);
        viewModel.testResponse.observe(viewLifecycleOwner, {

            if (!it?.isEmpty()!!) {

                binding.recyclerInstructions.adapter =
                    InstructionsAdapter(viewModel.testResponse.value?.get(0)?.examInstructions)
//                    InstructionsAdapter(viewModel.testResponse.value?.get(0)?.testSeries?.get(0)?.examInstructions)

            }
        })
    }


}
