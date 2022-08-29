package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class GetEmailById(
    private val repo: FirebaseRepository
) {

    operator fun invoke(email: String, id: String) = repo.getEmailById(email, id)
}