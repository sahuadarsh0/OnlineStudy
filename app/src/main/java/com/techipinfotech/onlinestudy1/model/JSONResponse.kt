package com.techipinfotech.onlinestudy1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class JSONResponse(

    @field:SerializedName("class_id")
    val classId: String? = null,

    @field:SerializedName("subjects")
    val subjects: List<SubjectsItem?>? = null,

    @field:SerializedName("notifications")
    val notifications: NotificationsItem? = null,

    @field:SerializedName("class_name")
    val className: String? = null
) : Parcelable

@Parcelize
data class NotificationsItem(

    @field:SerializedName("date")
    val date: String? = null,
    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class SubjectsItem(

    @field:SerializedName("subject_id")
    val subjectId: String? = null,


    @field:SerializedName("subject_image")
    val subjectImage: String? = null,

//    @field:SerializedName("chapters")
//    val chapters: List<ChaptersItem?>? = null,

    @field:SerializedName("subject_name")
    val subjectName: String? = null
) : Parcelable

@Parcelize
data class ChaptersItem(

    @field:SerializedName("topics")
    val topics: List<TopicsItem?>? = null,

    @field:SerializedName("chapter_name")
    val chapterName: String? = null,

    @field:SerializedName("chapter_id")
    val chapterId: String? = null
) : Parcelable

@Parcelize
data class TopicsItem(

    @field:SerializedName("topic_name")
    val topicName: String? = null,

    @field:SerializedName("topic_id")
    val topicId: String? = null,

    @field:SerializedName("content")
    val content: List<ContentItem?>? = null
) : Parcelable

@Parcelize
data class ContentItem(

    @field:SerializedName("video_viewed_Status")
    val videoViewedStatus: String? = null,

    @field:SerializedName("material_id")
    val materialId: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable
