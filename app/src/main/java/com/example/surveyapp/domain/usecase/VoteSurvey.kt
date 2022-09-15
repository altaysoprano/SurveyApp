package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.models.Option
import com.example.surveyapp.data.repository.FirebaseRepository
import java.util.*

class VoteSurvey(
    private val repo: FirebaseRepository
) {
    suspend operator fun invoke(
        email: String,
        id: String,
        optionId: Int,
        options: List<Option>,
        surveyTitle: String,
        deadline: Date
    ) = repo.voteSurvey(emailName = email, id = id, optionId = optionId,
        options = options, surveyTitle = surveyTitle, surveyDeadline = deadline)
}