package com.nimble.nimblesurveys.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimble.nimblesurveys.data.repository.SurveyRepository
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val repository: SurveyRepository
) : ViewModel() {

    private val _surveyResponse = MutableLiveData<Resource<List<SurveyData>?>>()
    val surveyResponse: LiveData<Resource<List<SurveyData>?>> = _surveyResponse

    fun fetchSurveys() {
        viewModelScope.launch {
            try {
                _surveyResponse.value = Resource.loading()
                val response = repository.fetchSurveys()
                _surveyResponse.value = response
            } catch (e: Exception) {
                _surveyResponse.value = Resource.error(e.message.toString())
            }
        }
    }

}