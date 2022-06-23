package com.techipinfotech.onlinestudy1

enum class API(private val text: String) {
    URL("http://mma.brsofttech.in/"),
    //URL("http://drmitraapp.aicpnews.in/"),
    //    URL("http://pragatiinstitute.co.in/app/"),
    //    URL("http://pragati.techipinfotech.com/"),
    //    URL("http://192.168.29.204:8m2/pragatiapp/"),
    TIPI(URL.toString() + "tipilogin/"),
    DEMO_URL(TIPI.toString() + "demo_video/"),
    HOME_URL(TIPI.toString() + "topics_material_approval/"),
    LOGIN_URL(TIPI.toString() + "login/"),
    TEST_URL(TIPI.toString() + "testentry/"),
    TEST1_URL(TIPI.toString() + "testentry1/"),
    NEW_TEST_URL(TIPI.toString() + "getexam/"),
    GIVEN_ANSWER_URL(TEST1_URL.toString() + "givenanswer/"),
    VIEW_QUESTION_URL(TEST1_URL.toString() + "showquestion/"),
    ASSETS_URL(URL.toString() + "uploads/tipilogin/"),
    STUDENT(ASSETS_URL.toString() + "student/"),
    SUBJECT(ASSETS_URL.toString() + "subject/"),
    PDF(ASSETS_URL.toString() + "pdf/"),
    TEST(ASSETS_URL.toString() + "test/"),
    EXAM(ASSETS_URL.toString() + "exam/"),
    QUESTION(ASSETS_URL.toString() + "question/");

    override fun toString(): String {
        return text
    }
}