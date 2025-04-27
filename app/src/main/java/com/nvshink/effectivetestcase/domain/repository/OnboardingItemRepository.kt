package com.nvshink.effectivetestcase.domain.repository

import com.nvshink.effectivetestcase.data.model.OnboardingItem

interface OnboardingItemRepository {
    fun getOnboardingItems(): List<OnboardingItem>
}