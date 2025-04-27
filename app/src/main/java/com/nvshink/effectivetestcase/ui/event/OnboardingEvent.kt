package com.nvshink.effectivetestcase.ui.event

import com.nvshink.effectivetestcase.data.model.OnboardingItem


sealed interface OnboardingEvent {
    data class UpdateOnboardingItem(val index: Int, val isSelected: Boolean): OnboardingEvent
    data object hideOnboarding: OnboardingEvent
}