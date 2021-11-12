package com.techipinfotech.onlinestudy1;

import org.jetbrains.annotations.NotNull;

public enum API {

        URL("http://drmitraapp.aicpnews.in/"),
//        URL("http://pragatiinstitute.co.in/app/"),
//    URL("http://pragati.techipinfotech.com/"),
//    URL("http://192.168.29.204:8m2/pragatiapp/"),

    TIPI(URL + "tipilogin/"),

    HOME_URL(TIPI + "topics_material_approval/"),
    LOGIN_URL(TIPI + "login/"),
    TEST_URL(TIPI + "testentry/"),
    TEST1_URL(TIPI + "testentry1/"),
    NEW_TEST_URL(TIPI + "getexam/"),
    GIVEN_ANSWER_URL(TEST1_URL + "givenanswer/"),
    VIEW_QUESTION_URL(TEST1_URL + "showquestion/"),

    ASSETS_URL(URL + "uploads/tipilogin/"),

    STUDENT(ASSETS_URL + "student/"),
    SUBJECT(ASSETS_URL + "subject/"),
    PDF(ASSETS_URL + "pdf/"),
    TEST(ASSETS_URL + "test/"),
    EXAM(ASSETS_URL + "exam/"),
    QUESTION(ASSETS_URL + "question/");

    private final String text;

    API(final String text) {
        this.text = text;
    }

    @NotNull
    @Override
    public String toString() {
        return text;
    }
}