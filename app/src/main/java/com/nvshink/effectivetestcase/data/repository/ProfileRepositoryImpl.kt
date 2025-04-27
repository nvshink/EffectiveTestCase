package com.nvshink.effectivetestcase.data.repository

import com.nvshink.effectivetestcase.data.model.Profile
import com.nvshink.effectivetestcase.domain.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(): ProfileRepository {
    override fun setEmail(email: String) {
        Profile.email = email
    }

    override fun getProfile(): Profile {
        return Profile
    }
}