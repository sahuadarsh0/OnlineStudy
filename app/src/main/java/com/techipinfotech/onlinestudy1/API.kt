package com.techipinfotech.onlinestudy1

enum class API(private val text: String) {
    URL("http://mma.brsofttech.in/"),
    TIPI(URL.toString() + "tipilogin/"),
    DEMO_URL(TIPI.toString() + "demo_video/"),
    HOME_URL(TIPI.toString() + "topics_material_approval/"),
    LOGIN_URL(TIPI.toString() + "login/"),
    ASSETS_URL(URL.toString() + "uploads/tipilogin/"),
    STUDENT(ASSETS_URL.toString() + "student/"),
    SUBJECT(ASSETS_URL.toString() + "subject/"),
    PDF(ASSETS_URL.toString() + "pdf/");

    override fun toString(): String {
        return text
    }
}