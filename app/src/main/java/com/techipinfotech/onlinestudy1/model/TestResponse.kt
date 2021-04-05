package com.techipinfotech.onlinestudy1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TestResponse(

	@field:SerializedName("course_name")
	val courseName: String? = null,

	@field:SerializedName("class_id")
	val classId: String? = null,

	@field:SerializedName("testseries")
	val testSeries: List<TestSeriesItem?>? = null
	,

	@field:SerializedName("exam_instructions")
	val examInstructions: List<ExamInstructionsItem?>? = null
)

@Parcelize
data class TestSeriesItem(

	@field:SerializedName("exams")
	val exams: List<ExamsItem?>? = null,

	@field:SerializedName("test_image")
	val testImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("test_name")
	val testName: String? = null,

	@field:SerializedName("test_id")
	val testId: String? = null

) : Parcelable

@Parcelize
data class ExamsItem(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("exam_image")
	val examImage: String? = null,

	@field:SerializedName("exam_name")
	val examName: String? = null,

	@field:SerializedName("exam_id")
	val examId: String? = null,

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null

) : Parcelable

@Parcelize
data class ResultItem(

	@field:SerializedName("totalqestions")
	val totalQuestions: String? = null,

	@field:SerializedName("obtain")
	val obtain: String? = null,

	@field:SerializedName("skipped")
	val skipped: String? = null,

	@field:SerializedName("wronganswer")
	val wrongAnswer: String? = null,

	@field:SerializedName("rightanswer")
	val rightAnswer: String? = null,

	@field:SerializedName("viewpaper")
	val viewPaper: String? = null,

	@field:SerializedName("givenanswer")
	val givenAnswer: String? = null,

	@field:SerializedName("totalrightmarks")
	val totalRightMarks: String? = null,

	@field:SerializedName("totalwrongmarks")
	val totalWrongMarks: String? = null
) : Parcelable

@Parcelize
data class QuestionsItem(

	@field:SerializedName("question_id")
	val questionId: String? = null,

	@field:SerializedName("question_type")
	val questionType: String? = null,

	@field:SerializedName("questionimage")
	val questionImage: String? = null,

	@field:SerializedName("opt1")
	val opt1: String? = null,

	@field:SerializedName("opt2")
	val opt2: String? = null,

	@field:SerializedName("opt3")
	val opt3: String? = null,

	@field:SerializedName("opt4")
	val opt4: String? = null,

	@field:SerializedName("rightanswer")
	val rightAnswer: String? = null,

	@field:SerializedName("opt1e")
	val opt1e: String? = null,

	@field:SerializedName("opt2e")
	val opt2e: String? = null,

	@field:SerializedName("opt3e")
	val opt3e: String? = null,

	@field:SerializedName("opt4e")
	val opt4e: String? = null,

	@field:SerializedName("test_id")
	val testId: String? = null,

	@field:SerializedName("exam_id")
	val examId: String? = null
) : Parcelable

@Parcelize
data class AnswersItem(

	@field:SerializedName("question_id")
	val questionId: String? = null,

	@field:SerializedName("rightanswer")
	val rightAnswer: String? = null,

	@field:SerializedName("givenanswer")
	val givenAnswer: String? = null,

	@field:SerializedName("test_id")
	val testId: String? = null,

	@field:SerializedName("exam_id")
	val examId: String? = null,

	@field:SerializedName("class_id")
	val classId: String? = null,

	@field:SerializedName("student_mobile")
	val studentMobile: String? = null
) : Parcelable

@Parcelize
data class ExamInstructionsItem(
	@field:SerializedName("exam_instruction")
	val examInstruction: String? = null
) : Parcelable


