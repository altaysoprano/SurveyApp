package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository
import javax.inject.Inject

class GetSurveys(
    private val repo: FirebaseRepository
) {
    operator fun invoke() = repo.getBooksFromFirestore()
}
