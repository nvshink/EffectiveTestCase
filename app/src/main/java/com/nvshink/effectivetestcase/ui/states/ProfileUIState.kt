package com.nvshink.effectivetestcase.ui.states

import com.nvshink.effectivetestcase.data.model.Profile

data class ProfileUIState (
    val profile: Profile? = null,
    val isAuthorized: Boolean? = null,
    val email: String = "",
    val password: String = "",
    val isErrorLogIn: Boolean = false
)