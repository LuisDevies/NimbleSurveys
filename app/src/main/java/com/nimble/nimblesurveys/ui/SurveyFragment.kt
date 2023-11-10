package com.nimble.nimblesurveys.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.nimble.nimblesurveys.R
import com.nimble.nimblesurveys.databinding.FragmentSurveyBinding
import com.nimble.nimblesurveys.model.survey.Survey
import com.nimble.nimblesurveys.utils.DateUtil
import com.squareup.picasso.Picasso


private const val ARG_SURVEY = "arg_survey"
private const val ARG_PAGES = "arg_pages"
private const val ARG_CURRENT = "arg_current"

class SurveyFragment : Fragment() {

    private var survey: Survey? = null
    private var pages: Int = 0
    private var currentPage: Int = 0

    private lateinit var binding: FragmentSurveyBinding

    companion object {
        fun newInstance(survey: Survey, pages: Int, currentPage: Int) =
            SurveyFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SURVEY, survey)
                    putInt(ARG_PAGES, pages)
                    putInt(ARG_CURRENT, currentPage)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            survey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_SURVEY, Survey::class.java)
            } else {
                it.getParcelable(ARG_SURVEY)
            }
            pages = it.getInt(ARG_PAGES)
            currentPage = it.getInt(ARG_CURRENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSurveyBinding.inflate(layoutInflater)
        val view = binding.root

        binding.surveyTitleTextView.text = survey?.title
        binding.surveyDescriptionTextView.text = survey?.description
        binding.dateTextView.text = DateUtil.getTodayDate()
        binding.detailScreenTextView.setOnClickListener { goToActivity(DetailActivity::class.java) }

        for (i in 0 until pages) {
            val imgDot = ImageButton(context)
            imgDot.tag = i

            imgDot.setBackgroundResource(0)
            imgDot.setPadding(5, 5, 5, 5)
            val params = LinearLayout.LayoutParams(30, 30)
            imgDot.layoutParams = params
            if (i == currentPage) {
                imgDot.setBackgroundResource(R.drawable.dot_indicator_selected)
            } else {
                imgDot.setBackgroundResource(R.drawable.dot_indicator_default)
            }
            binding.dotsLinear.addView(imgDot)
        }

        Picasso.get()
            .load(survey?.coverImage)
            .fit()
            .into(binding.background)

        return view
    }

    private fun goToActivity(activityToOpen: Class<out Activity?>) {
        val intent = Intent(context, activityToOpen)
        startActivity(intent)
    }
}