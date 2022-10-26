package com.example.surveyapp.presentation.poll_details

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatSurveyDate(date: Date?) : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val deadlineString = sdf.format(date)

        var dateString = deadlineString
        var odt = OffsetDateTime.parse(dateString)
        var dtf = DateTimeFormatter.ofPattern("MMM dd, uuuu HH:mm:ss", Locale.ENGLISH)

        return dtf.format(odt)

    }
}