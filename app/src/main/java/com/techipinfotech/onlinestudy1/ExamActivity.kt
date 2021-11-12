package com.techipinfotech.onlinestudy1

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.techipinfotech.onlinestudy1.databinding.ActivityExamBinding
import com.techipinfotech.onlinestudy1.db.QuestionDbHelper
import com.techipinfotech.onlinestudy1.model.AnswersItem
import com.techipinfotech.onlinestudy1.model.QuestionsItem
import com.techipinfotech.onlinestudy1.model.Received
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.androidutils.ProcessDialog
import technited.minds.androidutils.SharedPrefs
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class ExamActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExamBinding
    private lateinit var processDialog: ProcessDialog
    private lateinit var userSharedPreferences: SharedPrefs
    private lateinit var testSharedPreferences: SharedPrefs
    private var dbHelper = QuestionDbHelper(this)
    private var questionList: List<QuestionsItem?>? = null
    private var isExamRunning = false
    private var questionCounter = 0
    private var score = 0
    private var questionCountTotal = 0
    private var currentQuestion: QuestionsItem? = null
    private var answered = false
    private lateinit var url: String
    private lateinit var questionsBasket: ArrayList<String>
    private lateinit var zoomImageDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        processDialog = ProcessDialog(this)
//        processDialog.show()

        userSharedPreferences = SharedPrefs(this, "USER")
        testSharedPreferences = SharedPrefs(this, "TEST")
        testSharedPreferences.set("score", "0")

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        zoomImageDialog = Dialog(this)
        zoomImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        zoomImageDialog.setContentView(R.layout.dialog_zoom_image)
        val window: Window? = zoomImageDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawableResource(R.color.semi_transparent)
//        zoomImageDialog.setCancelable(true)

        val args: ExamActivityArgs by navArgs()
        val exam = args.exam


        questionsBasket = ArrayList<String>()


        getQuestionsList(exam.examId!!)

        binding.apply {
            examName.text = exam.examName
            examId.text = exam.examId
            totalTime.text = exam.duration + " min"
            timer.text = exam.duration + " min"
            val time = (exam.duration?.toInt() ?: 1) * 1000 * 60
            startStopButton.setOnClickListener {
                startExam(time)
            }
            studentName.text = userSharedPreferences.get("student_name")
            btnNext.setOnClickListener {
                showNextQuestion()
            }
            btnPrev.setOnClickListener {
                showPreviousQuestion()
            }
            answerGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

                when {
                    option1.isChecked -> {
                        checkAnswer(1)
                    }
                    option2.isChecked -> {
                        checkAnswer(2)
                    }
                    option3.isChecked -> {
                        checkAnswer(3)
                    }
                    option4.isChecked -> {
                        checkAnswer(4)
                    }
                }

            }


            questionImage.setOnClickListener {
                val imageUrl = API.QUESTION.toString() + currentQuestion!!.questionImage
                showZoomImage(imageUrl)
            }

            option1Image.setOnClickListener {
                val imageUrl = API.QUESTION.toString() + currentQuestion!!.opt1e
                showZoomImage(imageUrl)
            }

            option2Image.setOnClickListener {
                val imageUrl = API.QUESTION.toString() + currentQuestion!!.opt2e
                showZoomImage(imageUrl)
            }

            option3Image.setOnClickListener {
                val imageUrl = API.QUESTION.toString() + currentQuestion!!.opt3e
                showZoomImage(imageUrl)
            }

            option4Image.setOnClickListener {
                val imageUrl = API.QUESTION.toString() + currentQuestion!!.opt4e
                showZoomImage(imageUrl)
            }

        }


    }

    private fun getQuestionsList(examId: String) {

        dbHelper.deleteTableData()

        val getQuestionsList = TestApi.getApiService().getQuestion(examId)
        getQuestionsList.enqueue(object : Callback<List<QuestionsItem>> {

            override fun onFailure(call: Call<List<QuestionsItem>>, t: Throwable) {
                Toast.makeText(this@ExamActivity, "Error", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()
            }
            override fun onResponse(
                call: Call<List<QuestionsItem>>,
                response: Response<List<QuestionsItem>>
            ) {
                val received = response.body()
                processDialog.dismiss()
                if (received != null) {

                    dbHelper.addQuestions(received)
                    questionList = dbHelper.allQuestions


                    binding.totalQuestionCount.text = questionList?.size.toString()
                    questionCountTotal = questionList?.size!!


                }
            }
        })

    }

    private fun showZoomImage(imageUrl: String) {
        zoomImageDialog.show()
        val image = zoomImageDialog.findViewById(R.id.image) as PhotoView
        Glide
            .with(this@ExamActivity)
            .load(imageUrl)
            .placeholder(R.drawable.ic_triangle)
            .into(image)
    }

    private fun showNextQuestion() {
        binding.apply {
            answerGroup.clearCheck()
            question.smoothScrollTo(0, 0);
            when {
                questionCounter < questionCountTotal -> {
                    currentQuestion = questionList?.get(questionCounter)
                    if (currentQuestion!!.questionType == "I") {
                        questionText.visibility = View.GONE
                        questionImage.visibility = View.VISIBLE

                        url = API.QUESTION.toString() + currentQuestion!!.questionImage
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(questionImage)
                    } else {
                        questionImage.visibility = View.GONE
                        questionText.visibility = View.VISIBLE
                        questionText.text = currentQuestion!!.questionImage
                    }
                    if (currentQuestion!!.opt1 == "I") {

                        option1Image.visibility = View.VISIBLE
                        option1.text = ""

                        url = API.QUESTION.toString() + currentQuestion!!.opt1e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option1Image)
                    } else {
                        option1Image.visibility = View.GONE
                        option1.text = currentQuestion!!.opt1e
                    }
                    if (currentQuestion!!.opt2 == "I") {

                        option2Image.visibility = View.VISIBLE
                        option2.text = ""

                        url = API.QUESTION.toString() + currentQuestion!!.opt2e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option2Image)
                    } else {
                        option2Image.visibility = View.GONE
                        option2.text = currentQuestion!!.opt2e
                    }
                    if (currentQuestion!!.opt3 == "I") {

                        option3Image.visibility = View.VISIBLE
                        option3.text = ""
                        url = API.QUESTION.toString() + currentQuestion!!.opt3e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option3Image)
                    } else {
                        option3Image.visibility = View.GONE
                        option3.text = currentQuestion!!.opt3e
                    }
                    if (currentQuestion!!.opt4 == "I") {

                        option4Image.visibility = View.VISIBLE
                        option4.text = ""
                        url = API.QUESTION.toString() + currentQuestion!!.opt4e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option4Image)
                    } else {
                        option4Image.visibility = View.GONE
                        option4.text = currentQuestion!!.opt4e
                    }

                    questionCounter++
                    totalQuestionCount.text = " $questionCounter/$questionCountTotal"
                    answered = false
                }
                questionCounter == questionCountTotal -> {
                    btnNext.text = "Finish"
                    questionCounter++
                }
                else -> {
                    endExam()
                }
            }
        }
    }

    private fun showPreviousQuestion() {
        binding.apply {
            question.smoothScrollTo(0, 0);

            answerGroup.clearCheck()

            questionCounter--
            when {
                questionCounter > -1 -> {
                    currentQuestion = questionList?.get(questionCounter)
                    if (currentQuestion!!.questionType == "I") {
                        questionText.visibility = View.GONE
                        questionImage.visibility = View.VISIBLE

                        url = API.QUESTION.toString() + currentQuestion!!.questionImage
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(questionImage)
                    } else {
                        questionImage.visibility = View.GONE
                        questionText.visibility = View.VISIBLE
                        questionText.text = currentQuestion!!.questionImage
                    }
                    if (currentQuestion!!.opt1 == "I") {

                        option1Image.visibility = View.VISIBLE
                        option1.text = ""

                        url = API.QUESTION.toString() + currentQuestion!!.opt1e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option1Image)
                    } else {
                        option1Image.visibility = View.GONE
                        option1.text = currentQuestion!!.opt1e
                    }
                    if (currentQuestion!!.opt2 == "I") {

                        option2Image.visibility = View.VISIBLE
                        option2.text = ""

                        url = API.QUESTION.toString() + currentQuestion!!.opt2e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option2Image)
                    } else {
                        option2Image.visibility = View.GONE
                        option2.text = currentQuestion!!.opt2e
                    }
                    if (currentQuestion!!.opt3 == "I") {

                        option3Image.visibility = View.VISIBLE
                        option3.text = ""
                        url = API.QUESTION.toString() + currentQuestion!!.opt3e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option3Image)
                    } else {
                        option3Image.visibility = View.GONE
                        option3.text = currentQuestion!!.opt3e
                    }
                    if (currentQuestion!!.opt4 == "I") {

                        option4Image.visibility = View.VISIBLE
                        option4.text = ""
                        url = API.QUESTION.toString() + currentQuestion!!.opt4e
                        Glide
                            .with(this@ExamActivity)
                            .load(url)
                            .placeholder(R.drawable.ic_triangle)
                            .into(option4Image)
                    } else {
                        option4Image.visibility = View.GONE
                        option4.text = currentQuestion!!.opt4e
                    }

                    totalQuestionCount.text = " ${questionCounter + 1}/$questionCountTotal"
                    answered = false
                }
                questionCounter == questionCountTotal -> {
                    btnNext.text = "Finish"
                }
                else -> {
                    Toast.makeText(
                        this@ExamActivity,
                        "This is the first question",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }
    }

    private fun checkAnswer(answer: Int) {

        val answersItem = AnswersItem(
            currentQuestion?.questionId,
            currentQuestion?.rightAnswer,
            answer.toString(),
            currentQuestion?.testId,
            binding.examId.text.toString(),
            userSharedPreferences.get("class_id"),
            userSharedPreferences.get("student_mobile")
        )

        if (questionsBasket.contains(currentQuestion?.questionId)) {
            dbHelper.fixAnswer(answersItem)
        } else {
            currentQuestion?.questionId?.let { questionsBasket.add(it) }
            dbHelper.addAnswer(answersItem)

        }

        Log.d(
            "asa",
            "checkAnswer: $answer right answer : ${currentQuestion?.rightAnswer?.toInt() ?: -1}"
        )
        answered = true
        if (answer == currentQuestion?.rightAnswer?.toInt() ?: -1) {
            score++
            testSharedPreferences.set("score", score.toString())
        }

    }

    private fun startExam(time: Int) {

        isExamRunning = true
        showNextQuestion()
        binding.startStopButton.visibility = View.GONE
        binding.questionGroup.visibility = View.VISIBLE
        object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timer.text =
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec)
                if (min <= 15.toLong()) {
                    binding.paperBar.background = (getDrawable(R.color.red))
                }

            }

            override fun onFinish() {
                endExam()
            }
        }.start()
        Toast.makeText(this@ExamActivity, "Exam is Started", Toast.LENGTH_SHORT)
            .show()


    }

    private fun endExam() {
        isExamRunning = false
        binding.timer.text = "Exam Over"
        Toast.makeText(this@ExamActivity, "Exam Over", Toast.LENGTH_SHORT)
            .show()

        val answers = dbHelper.allAnswers
        processDialog.show()
        submitTest(answers)
    }

    private fun submitTest(answers: List<AnswersItem>) {
        processDialog.dismiss()
        finish()

        val submitTest =
            TestApi.getApiService().submittest(answers)
        submitTest.enqueue(object : Callback<Received?> {
            override fun onResponse(call: Call<Received?>, response: Response<Received?>) {

            }

            override fun onFailure(call: Call<Received?>, t: Throwable) {

            }
        })
    }

    override fun onBackPressed() {
        if (isExamRunning)
            Toast.makeText(this@ExamActivity, "Disabled", Toast.LENGTH_SHORT)
                .show()
        else
            super.onBackPressed()

    }


}