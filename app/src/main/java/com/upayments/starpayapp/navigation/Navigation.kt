package com.upayments.starpayapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.upayments.starpayapp.main_views.AppInitView
import com.upayments.starpayapp.main_views.OnboardUserView
import com.upayments.starpayapp.onboarding.OnboardingWelcomeView

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "app_init_view") {

        composable("app_init_view") {
            AppInitView(navController)
        }

        composable("onboard_user") {
            OnboardUserView(navController)
        }

        composable(
            route = "onboarding_welcome",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth ->  fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth ->  -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ) }
        ) {
            OnboardingWelcomeView()
        }
    }
}