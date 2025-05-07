package com.nvshink.effectivetestcase.ui.event


sealed interface OnboardingEvent {
    data class UpdateOnboardingItem(val index: Int, val isSelected: Boolean): OnboardingEvent
    data object HideOnboarding: OnboardingEvent
}