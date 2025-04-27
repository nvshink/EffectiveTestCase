package com.nvshink.effectivetestcase.ui.states

import com.nvshink.effectivetestcase.data.model.OnboardingItem

data class OnboardingUIState (
    val onboardingItemsList: List<OnboardingItem> = emptyList(),
    val isShow: Boolean = false
)