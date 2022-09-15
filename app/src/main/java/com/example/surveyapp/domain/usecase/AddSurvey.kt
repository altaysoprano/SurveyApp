package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.repository.FirebaseRepository
import java.util.*

class AddSurvey(
    private val repo: FirebaseRepository
) {
    suspend operator fun invoke(
        emailName: String,
        isOwnVoteChecked: Boolean,
        title: String,
        options: List<Option>,
        deadline: Date
    ) = repo.addSurveyToFirestore(isOwnVoteChecked, emailName, title, options, deadline)
}