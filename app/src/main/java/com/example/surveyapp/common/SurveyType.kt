package com.example.surveyapp.common

import android.os.Bundle
import androidx.navigation.NavType
import com.example.surveyapp.data.models.Survey
import com.google.gson.Gson

class SurveyType : NavType<Survey>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Survey? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Survey {
        return Gson().fromJson(value, Survey::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Survey) {
        bundle.putParcelable(key, value)
    }
}