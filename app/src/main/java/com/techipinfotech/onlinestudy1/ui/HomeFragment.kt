package com.techipinfotech.onlinestudy1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.techipinfotech.onlinestudy1.MainActivityViewModel
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.databinding.FragmentHomeBinding
import technited.minds.androidutils.SharedPrefs


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var animation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userSharedPreferences = SharedPrefs(context, "USER")

        binding.video.visibility = View.GONE
        binding.test.visibility = View.GONE

        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
        binding.video.startAnimation(animation)
        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        binding.test.startAnimation(animation)

        if (userSharedPreferences.get("student_subject_id") != null) {

            when (userSharedPreferences.get("student_subject_id")) {
                "1" -> binding.video.visibility = View.VISIBLE
                "2" -> binding.test.visibility = View.VISIBLE
                "3" -> {
                    binding.video.visibility = View.VISIBLE
                    binding.test.visibility = View.VISIBLE
                }

            }
        }
        binding.apply {
            video.setOnClickListener {
                it.findNavController().navigate(R.id.action_nav_home_to_subjectsFragment)
            }
            test.setOnClickListener {
                    it.findNavController().navigate(R.id.action_nav_home_to_onlineTestActivity2)
            }
        }


        viewModel.jsonResponse.observe(viewLifecycleOwner, {

            if (!it?.isEmpty()!!) {
                binding.className.text = viewModel.jsonResponse.value?.get(0)?.className
            }
        })
    }


}
