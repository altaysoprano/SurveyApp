package com.example.surveyapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Survey(
    var id: String? = null,
    val title: String = "",
    val options: List<Option> = listOf()
) : Parcelable
