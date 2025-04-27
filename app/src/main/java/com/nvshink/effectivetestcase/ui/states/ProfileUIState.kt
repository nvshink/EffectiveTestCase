package com.nvshink.effectivetestcase.ui.states

import com.nvshink.effectivetestcase.data.model.Profile

data class ProfileUIState (
    val profile: Profile? = null,
    val isAuthorized: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isErrorLogIn: Boolean = false
)