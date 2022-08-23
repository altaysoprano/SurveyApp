package com.example.surveyapp.utils

import androidx.compose.ui.graphics.Color
import com.example.surveyapp.ui.theme.*

fun parseIndexToColor(index: Int): Color {
    return when (index) {
        0 -> option1Color
        1 -> option2Color
        2 -> option3Color
        3 -> option4Color
        4 -> option5Color
        5 -> option6Color
        6 -> option7Color
        7 -> option8Color
        8 -> option9Color
        9 -> option10Color
        else -> Color.Black
    }
}