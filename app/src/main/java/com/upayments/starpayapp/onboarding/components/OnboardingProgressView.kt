package com.upayments.starpayapp.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upayments.starpayapp.state.OnboardingProgressContract
import com.upayments.starpayapp.state.enums.OnboardingPhase
import com.upayments.starpayapp.state.enums.OnboardingPhaseStatus
import com.upayments.starpayapp.ui.theme.AppMidGray
import com.upayments.starpayapp.ui.theme.MainAppColor

@Composable
fun OnboardingProgressView(
    // onboardingProgress: OnboardingProgressState = OnboardingProgressState
    onboardingProgress: OnboardingProgressContract
) {
    // Container Row that holds all circles and lines
    Row(
        modifier = Modifier
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        repeat(onboardingProgress.phasesCount) { index ->
            val phase = onboardingProgress.phases[index]
            val isCompleted = (phase.status == OnboardingPhaseStatus.COMPLETED)
            val isCurrentPhase = (onboardingProgress.currentPhaseIndex == index)

            // Circle + Checkmark or "Active" ring
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.wrapContentSize()
            ) {
                // Outer stroked circle (similar to .stroke(lineWidth: ...)
                // with a thick stroke if completed, thin if not:
                CircleWithStroke(
                    isCompleted = isCompleted
                )

                // If completed, show the checkmark
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed phase",
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp) // to mimic SwiftUI's .font(.system(size: 14))
                    )
                }

                // If the current phase is in-progress (not completed), show an extra ring + inner fill
                if (isCurrentPhase && !isCompleted) {
                    // The outer stroke ring
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                    ) {
                        CircularStrokeRing()
                    }
                    // The smaller filled circle in the center
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MainAppColor)
                            .align(Alignment.Center)
                    )
                }
            }

            // Draw the line after each circle (except the last)
            if (index < onboardingProgress.phasesCount - 1) {
                // ZStack-like approach: one line in appMidGray as background,
                // then a line in mainAppColor if this phase is completed.
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        // Choose a width that spaces out the circles. 30dp or any suitable value:
                        .width(40.dp)
                ) {
                    // Background line
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppMidGray)
                    )
                    // Foreground line (only visible if the phase is completed)
                    if (isCompleted) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MainAppColor)
                        )
                    }
                }
            }
        }
    }
}

/**
 * A helper composable that draws a circle using Canvas with a different stroke width
 * and color based on whether the phase is completed or not.
 */
@Composable
private fun CircleWithStroke(
    isCompleted: Boolean
) {
    // Circle in SwiftUI was:
    //   .frame(width: 20, height: isCompleted ? 15 : 20)
    //   .stroke(lineWidth: isCompleted ? 15 : 3)
    // We'll replicate the logic with a Canvas:

    // For a thick stroke, we do 15f px. For a thin stroke, 3f px:
    val strokeWidthDp = if (isCompleted) 20.dp else 3.dp
    val circleSizeWidth = 20.dp
    val circleSizeHeight = 20.dp

    Box(
        modifier = Modifier
            .size(width = circleSizeWidth, height = circleSizeHeight)
    ) {
        // Draw a circle with stroke
        val strokeWidthPx = with(LocalDensity.current) { strokeWidthDp.toPx() }
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = if (isCompleted) MainAppColor else AppMidGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round
                ),
                size = Size(size.width, size.height)
            )
        }
    }
}

/**
 * Draws a ring of 3dp stroke in MainAppColor. This is to highlight
 * the *current* phase if it's not completed.
 */
@Composable
private fun CircularStrokeRing() {
    val strokeWidthPx = with(LocalDensity.current) { 3.dp.toPx() }
    androidx.compose.foundation.Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawArc(
            color = MainAppColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidthPx,
                cap = StrokeCap.Round
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingProgressViewPreview() {
    val mockOnboardingProgressState = object : OnboardingProgressContract {
        override var phases: MutableList<OnboardingPhase> = mutableListOf(
            OnboardingPhase(1, "Inicio", OnboardingPhaseStatus.COMPLETED, true),
            OnboardingPhase(2, "Verificación de Identidad", OnboardingPhaseStatus.COMPLETED, true),
            OnboardingPhase(3, "Aceptación de Contrato", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(4, "Aceptación de Términos", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(5, "Aceptación de Política de Privacidad", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(6, "Datos Adicionales y Confirmaciones", OnboardingPhaseStatus.DISABLED, true),
            OnboardingPhase(7, "Confirmación Final", OnboardingPhaseStatus.DISABLED, true)
        )
        override val currentPhaseIndex = 2
    }

    OnboardingProgressView(
        onboardingProgress = mockOnboardingProgressState
    )
}