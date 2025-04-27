package com.nvshink.effectivetestcase.ui.screen.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nvshink.effectivetestcase.R
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.states.OnboardingUIState

@Composable
fun OnboardingScreen(
    uiState: OnboardingUIState,
    onEvent: (OnboardingEvent) -> Unit,
    onContinueClicked: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(R.string.onboarding_title),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(64.dp)
            )
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Fixed(5),
                modifier = Modifier
                    .height(320.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalItemSpacing = 8.dp
            ) {
                itemsIndexed(uiState.onboardingItemsList) { index, item ->
                    val rotationAngle by animateFloatAsState(
                        targetValue = (if (item.isSelected) (if (item.isRotateAnglePositive) 15f else (-15f)) else 0f),
                        animationSpec = tween(300)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Transparent)
                                )
                            )

                    ) {
                        Button(
                            onClick = {
                                onEvent(
                                    OnboardingEvent.UpdateOnboardingItem(
                                        index,
                                        !item.isSelected
                                    )
                                )
                            }, modifier = Modifier
                                .height(64.dp)
//                            .graphicsLayer(
//                                renderEffect =  BlurEffect(radiusX = 10f, radiusY = 10f, edgeTreatment = TileMode.Decal)
//                            ) TODO
//                            .rotate(if (item.isSelected) (if (item.isRotateAnglePositive) rotationAngle else (-1 * rotationAngle)) else 0f),
                                .rotate(rotationAngle),
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = if (item.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                text = stringResource(item.titleResource),
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.button_continue_name),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}