package com.nvshink.effectivetestcase.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.effectivetestcase.data.datastore.AppPreferences
import com.nvshink.effectivetestcase.data.model.FavoriteCourse
import com.nvshink.effectivetestcase.domain.repository.ProfileRepository
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.states.CourseUIState
import com.nvshink.effectivetestcase.ui.states.ProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {
    private val _profile = MutableStateFlow(repository.getProfile())

    private val _uiState = MutableStateFlow(ProfileUIState())

    private val _authorized = appPreferences.authorized

    val uiState = combine(_uiState, _profile, _authorized) { uiState, profile, authorized ->
        uiState.copy(
            profile = profile,
            isAuthorized = authorized
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProfileUIState())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LogIn -> {
                _uiState.update {
                    if (it.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
                        viewModelScope.launch {
                            appPreferences.setAuthorized(true)
                        }
                        repository.setEmail(email = it.email)
                        it.copy(
                            profile = _profile.value,
                            isErrorLogIn = false,
                            isAuthorized = true
                        )
                    } else {
                        it.copy(
                            isErrorLogIn = true
                        )
                    }
                }
            }

            ProfileEvent.LogOut -> {
                _uiState.update {
                    viewModelScope.launch {
                        appPreferences.setAuthorized(false)
                    }
                    it.copy(
                        isAuthorized = false,
                        email = "",
                        password = ""
                    )
                }
            }

            is ProfileEvent.SetEmail -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        isErrorLogIn = false
                    )
                }
            }

            is ProfileEvent.SetPassword -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        isErrorLogIn = false
                    )
                }
            }
        }
    }
}