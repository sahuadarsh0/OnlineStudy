package com.techipinfotech.onlinestudy1.model

import com.google.gson.annotations.SerializedName

data class Received(

    @field:SerializedName("student_data")
    val studentData: StudentData? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class StudentData(

    @field:SerializedName("student_gender")
    val studentGender: String? = null,

    @field:SerializedName("total_fees")
    val totalFees: String? = null,

    @field:SerializedName("class_id")
    val classId: String? = null,

    @field:SerializedName("class_name")
    val className: String? = null,

    @field:SerializedName("student_id")
    val studentId: String? = null,

    @field:SerializedName("admission_last_date")
    val admissionLastDate: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("student_subject_id")
    val studentSubjectId: String? = null,

    @field:SerializedName("student_dob")
    val studentDob: String? = null,

    @field:SerializedName("remark")
    val remark: String? = null,

    @field:SerializedName("addmission_date")
    val addmissionDate: String? = null,

    @field:SerializedName("student_mobile")
    val studentMobile: String? = null,

    @field:SerializedName("reference")
    val reference: String? = null,

    @field:SerializedName("student_name")
    val studentName: String? = null,

    @field:SerializedName("student_address")
    val studentAddress: String? = null,

    @field:SerializedName("student_email")
    val studentEmail: String? = null,

    @field:SerializedName("student_father")
    val studentFather: String? = null,

    @field:SerializedName("viewed_status")
    val viewedStatus: String? = null
)
