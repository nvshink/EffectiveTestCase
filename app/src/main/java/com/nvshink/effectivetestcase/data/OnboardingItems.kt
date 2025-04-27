package com.nvshink.effectivetestcase.data

import com.nvshink.effectivetestcase.R
import com.nvshink.effectivetestcase.data.model.OnboardingItem

object OnboardingItems {
    private val listOfItems =
        listOf(
            OnboardingItem(titleResource = R.string.onboarding_1c_administration, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_rabbit_mq, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_traffic, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_content_making, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_b2b_marketing, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_google_analytics, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_ux_researcher, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_web_analytics, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_big_data, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_game_design, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_web_design, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_cinema_4d, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_promt_engineering, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_webflow, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_three_js, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_parsing, isSelected = false),
            OnboardingItem(titleResource = R.string.onboarding_python_develop, isSelected = false)
        )
    fun getOnboardingItems(): List<OnboardingItem> {
        return listOfItems
    }
}