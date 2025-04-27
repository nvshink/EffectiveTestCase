package com.nvshink.effectivetestcase.domain.repository

import com.nvshink.effectivetestcase.data.model.Profile

interface ProfileRepository {
    fun setEmail(email: String)
    fun getProfile(): Profile
}