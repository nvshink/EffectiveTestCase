package com.nvshink.effectivetestcase.data.repository

import com.nvshink.effectivetestcase.data.OnboardingItems
import com.nvshink.effectivetestcase.data.model.OnboardingItem
import com.nvshink.effectivetestcase.domain.repository.OnboardingItemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingItemRepositoryImpl @Inject constructor(): OnboardingItemRepository {
    private val onboardingItems = OnboardingItems.getOnboardingItems()
    override fun getOnboardingItems(): List<OnboardingItem> = onboardingItems
}