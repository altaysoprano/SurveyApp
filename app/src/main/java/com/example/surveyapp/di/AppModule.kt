package com.example.surveyapp.di

import com.example.surveyapp.common.Constants.SURVEYS
import com.example.surveyapp.data.firebase.FirebaseAuthLoginSourceProvider
import com.example.surveyapp.data.repository.FirebaseRepository
import com.example.surveyapp.domain.usecase.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideSurveysRef(
        db: FirebaseFirestore
    ) = db.collection(SURVEYS)

    @Provides
    @Singleton
    fun provideBooksRepository(
        surveysRef: CollectionReference
    ): FirebaseRepository = FirebaseRepository(FirebaseAuthLoginSourceProvider(), surveysRef)

    @Provides
    @Singleton
    fun provideUseCases(
        repo: FirebaseRepository
    ) = UseCases(
        getSurveys = GetSurveys(repo),
        addSurvey = AddSurvey(repo),
        firebaseAuthUseCase = FirebaseAuthUseCase(repo),
        getSurveyById = GetSurveyById(repo),
        voteSurvey = VoteSurvey(repo),
        getEmailById = GetEmailById(repo)
    )


}