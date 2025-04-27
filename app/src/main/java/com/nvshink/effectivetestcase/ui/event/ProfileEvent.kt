package com.nvshink.effectivetestcase.ui.event

import com.nvshink.effectivetestcase.data.model.Profile

sealed interface ProfileEvent {
    data class SetEmail (val email: String): ProfileEvent
    data class SetPassword (val password: String): ProfileEvent
    data object LogOut: ProfileEvent
    data object LogIn: ProfileEvent
}