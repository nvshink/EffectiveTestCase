package com.nvshink.effectivetestcase.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nvshink.effectivetestcase.R
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.states.ProfileUIState

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    profileUIState: ProfileUIState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp)
        .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = stringResource(R.string.profile_screen_hello) + (profileUIState.profile?.email
                ?: ""),
            style = MaterialTheme.typography.displaySmall
        )
        OutlinedButton(
            onClick = { onEvent(ProfileEvent.LogOut) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_log_out_name))
        }
    }
}