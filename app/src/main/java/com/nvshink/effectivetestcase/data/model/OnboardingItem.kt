package com.nvshink.effectivetestcase.data.model

import androidx.annotation.StringRes

/**
 * A class which represents item showing on the onboarding screen.
 * @param titleResource String resource ID.
 * @param isSelected Determines the selection of an item.
 * @param isRotateAnglePositive Defines the direction in which the object will be turned.
 */
data class OnboardingItem(
    @StringRes val titleResource: Int,
    val isSelected: Boolean,
    val isRotateAnglePositive: Boolean = false
)