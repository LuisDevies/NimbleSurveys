package com.nimble.nimblesurveys.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.nimblesurveys.data.repository.SurveyRepository
import com.nimble.nimblesurveys.model.survey.SurveyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val repository: SurveyRepository
) : ViewModel() {

    private val _surveyResponse = MutableLiveData<SurveyResponse>()
    val surveyResponse: LiveData<SurveyResponse> = _surveyResponse

    fun login() {
        viewModelScope.launch {
            try {
                val response = repository.fetchSurveys()
                _surveyResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}