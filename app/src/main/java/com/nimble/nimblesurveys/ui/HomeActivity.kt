package com.nimble.nimblesurveys.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.nimble.nimblesurveys.databinding.ActivityHomeBinding
import com.nimble.nimblesurveys.model.SurveyData
import com.nimble.nimblesurveys.utils.Resource
import com.nimble.nimblesurveys.viewmodel.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : FragmentActivity() {

    val viewModel: SurveyViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.surveyResponse.observe(this) {
            handleSurveys(it)
        }

        viewModel.fetchSurveys()

    }

    private fun handleSurveys(surveysResponse: Resource<List<SurveyData>?>) {
        when (surveysResponse.status) {
            Resource.Status.SUCCESS -> {
                surveysResponse.data?.let {
                    val surveyPagerViewPagerAdapter = SurveyViewPagerAdapter(this, it)
                    binding.surveyViewPager.adapter = surveyPagerViewPagerAdapter
                }
                binding.loading.visibility = View.GONE
            }

            Resource.Status.ERROR -> {
                Toast.makeText(this, surveysResponse.message, Toast.LENGTH_LONG).show()
                binding.loading.visibility = View.GONE
            }

            Resource.Status.LOADING -> {
                binding.loading.visibility = View.VISIBLE
            }
        }
    }
}