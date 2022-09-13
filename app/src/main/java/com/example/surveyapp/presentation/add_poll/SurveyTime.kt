package com.example.surveyapp.presentation.add_poll

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyTime(
    val day: Int,
    val hour: Int,
    val minute: Int
): Parcelable
