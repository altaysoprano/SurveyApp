package com.example.surveyapp.data.models

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Survey(
    var id: String? = null,
    val title: String = "",
    val options: List<Option> = listOf(),
    @ServerTimestamp
    var surveyAddedTimestamp: Date? = null
    ) : Parcelable
