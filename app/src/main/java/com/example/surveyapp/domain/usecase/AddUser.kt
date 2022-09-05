package com.example.surveyapp.domain.usecase

import com.example.surveyapp.data.repository.FirebaseRepository

class AddUser(
    private val repo: FirebaseRepository
) {

    suspend operator fun invoke(
        email: String
    ) = repo.getUser(email = email)
}