package com.example.surveyapp.data.models

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    var numberOfVotes: Int = 0,
    val isNewOption: Boolean = false
) : Parcelable
