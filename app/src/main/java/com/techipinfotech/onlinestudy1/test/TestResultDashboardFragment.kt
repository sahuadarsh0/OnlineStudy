package com.techipinfotech.onlinestudy1.test

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.db.williamchart.ExperimentalFeature
import com.db.williamchart.slidertooltip.SliderTooltip
import com.techipinfotech.onlinestudy1.API
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.databinding.FragmentTestResultDashboardBinding
import com.techipinfotech.onlinestudy1.model.ResultItem
import technited.minds.androidutils.SharedPrefs


class TestResultDashboardFragment : Fragment() {

    private lateinit var binding: FragmentTestResultDashboardBinding
    private lateinit var result: ResultItem
    private lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_test_result_dashboard,
            container,
            false
        )

        return binding.root

    }

    @OptIn(ExperimentalFeature::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: TestResultDashboardFragmentArgs by navArgs()

        result = args.result
        val rightAnswer: Float = result.rightAnswer?.toFloat() ?: 0.0f
        val wrongAnswer: Float = result.wrongAnswer?.toFloat() ?: 0.0f
        val skipped: Float = result.skipped?.toFloat() ?: 0.0f

        val lineSet = listOf(
            "Right" to rightAnswer,
            "Wrong" to wrongAnswer,
            "Skip" to skipped
        )

        binding.apply {

            chart.barsColorsList = listOf(
                resources.getColor(R.color.quantum_googgreen),
                resources.getColor(R.color.quantum_googred),
                resources.getColor(R.color.grey),
            )

            chart.animation.duration = animationDuration
            chart.tooltip =
                SliderTooltip().also {
                    it.color = Color.WHITE
                }
            chart.onDataPointTouchListener = { index, _, _ ->
                lineChartValue.text =
                    lineSet.toList()[index]
                        .second
                        .toString().removeSuffix(".0")
            }
            chart.animate(lineSet)


            obtainedMarks.text = "Obtained Marks : " + result.obtain.toString()
            totalRightMarks.text = "Total Right Marks : " + result.totalRightMarks.toString()
            totalWrongMarks.text = "Total Negative Marks : " + result.totalWrongMarks.toString()
            viewQuestions.setOnClickListener {
                val url = API.VIEW_QUESTION_URL.toString() + result.viewPaper!!
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url.replace("_","/")))
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.setPackage("com.android.chrome")
                try {
                    requireContext().startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "unable to open chrome", Toast.LENGTH_SHORT).show()
                    i.setPackage(null)
                    requireContext().startActivity(i)
                }
//                val action =
//                    TestResultDashboardFragmentDirections.actionTestResultDashboardFragmentToWebSiteActivity(
//                        url
//                    )
//                it.findNavController()
//                    .navigate(action)
            }
            viewAnswers.setOnClickListener {
                val url = API.GIVEN_ANSWER_URL.toString() + result.givenAnswer!!
                Log.d("asa", "onViewCreated: $url")
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url.replace("_","/")))
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.setPackage("com.android.chrome")
                try {
                    requireContext().startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "unable to open chrome", Toast.LENGTH_SHORT).show()
                    i.setPackage(null)
                    requireContext().startActivity(i)
                }
//                val action =
//                    TestResultDashboardFragmentDirections.actionTestResultDashboardFragmentToWebSiteActivity(
//                        url.replace("_","/")
//                    )
//                it.findNavController()
//                    .navigate(action)
            }

        }

    }

    companion object {

        private const val animationDuration = 1000L
    }


}
