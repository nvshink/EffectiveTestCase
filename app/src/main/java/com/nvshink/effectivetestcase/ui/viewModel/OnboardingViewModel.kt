package com.nvshink.effectivetestcase.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.effectivetestcase.data.datastore.AppPreferences
import com.nvshink.effectivetestcase.domain.repository.OnboardingItemRepository
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.states.OnboardingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    repository: OnboardingItemRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private var _onboardingItemsList = repository.getOnboardingItems()

    private val _isShow = appPreferences.onboardingIsShow

    private val _uiState = MutableStateFlow(OnboardingUIState())

    val uiState = combine(_uiState, _isShow) { uiState, isShow ->
        uiState.copy(
            isShow = isShow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OnboardingUIState())

    init {
        _uiState.update {
            it.copy(
                onboardingItemsList = _onboardingItemsList
            )
        }
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.UpdateOnboardingItem -> {
                _uiState.update {
//                    _onboardingItemsList =

                    it.copy(
                        onboardingItemsList = it.onboardingItemsList.mapIndexed { index, onboardingItem ->
                            if (index == event.index) onboardingItem.copy(
                                isSelected = event.isSelected,
                                isRotateAnglePositive = (0..1).random() == 0
                            ) else onboardingItem
                        }
                    )
                }
//                viewModelScope.launch {
//                    _onboardingItemsListFlow.update {
//                        _onboardingItemsList.mapIndexed { index, onboardingItem ->
//                            if (index == event.index) onboardingItem.copy(
//                                isSelected = event.isSelected,
//                                isRotateAnglePositive = (0..1).random() == 0
//                            ) else onboardingItem
//                        }
//                    }
//                }
            }

            is OnboardingEvent.HideOnboarding -> {
                viewModelScope.launch {
                    appPreferences.setOnboardingIsShow(false)
                }
            }
        }
    }
}