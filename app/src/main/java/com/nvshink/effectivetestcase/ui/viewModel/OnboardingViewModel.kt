package com.nvshink.effectivetestcase.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.effectivetestcase.data.datastore.AppPreferences
import com.nvshink.effectivetestcase.domain.repository.OnboardingItemRepository
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.states.OnboardingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    repository: OnboardingItemRepository,
    private val appPreferences: AppPreferences
):ViewModel() {
    private val onboardingItemsList = repository.getOnboardingItems()

    private val _uiState = MutableStateFlow(OnboardingUIState())

    val uiState: StateFlow<OnboardingUIState> = _uiState.asStateFlow()

    val onboardingShown: StateFlow<Boolean> = appPreferences.onboardingShown
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    onboardingItemsList = onboardingItemsList
                )
            }
        }
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.UpdateOnboardingItem -> {
                _uiState.update {
                    it.copy(onboardingItemsList = uiState.value.onboardingItemsList.mapIndexed { index, onboardingItem ->
                        if (index == event.index) onboardingItem.copy(
                            isSelected = event.isSelected,
                            isRotateAnglePositive = (0..1).random() == 0
                        ) else onboardingItem
                    })
                }
            }
            is OnboardingEvent.hideOnboarding -> {
                viewModelScope.launch {
                    appPreferences.setOnboardingShown(true)
                }
            }
        }
    }
}