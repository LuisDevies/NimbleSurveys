package com.nimble.nimblesurveys.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nimble.nimblesurveys.model.SurveyData


class SurveyViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val surveys: List<SurveyData>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = surveys.size

    override fun createFragment(position: Int): Fragment {
        val survey = surveys[position].attributes
        return SurveyFragment.newInstance(survey,surveys.size,position)
    }
}