package com.nvshink.effectivetestcase.data.model

import androidx.annotation.StringRes

data class OnboardingItem(
    @StringRes val titleResource: Int,
    val isSelected: Boolean,
    val isRotateAnglePositive: Boolean = false
)