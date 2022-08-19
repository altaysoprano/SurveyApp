package com.example.surveyapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val name: String = "",
    val numberOfVotes: Int = 0,
    val isNewOption: Boolean = false
) : Parcelable
