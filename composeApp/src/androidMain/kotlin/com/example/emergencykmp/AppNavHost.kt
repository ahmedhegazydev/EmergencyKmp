package com.example.emergencykmp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emergencykmp.mvi.EmergencyScreenSettings
import com.example.emergencykmp.mvi.Routes

@Composable
fun AppNavHost(
    onRequestPermissions: () -> Unit=  {},

    ) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
//        startDestination = Routes.Emergency
        startDestination = "splash"

    ) {
        composable("splash") {
            SplashScreen {
                navController.navigate("emergency") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable(Routes.Emergency) {
            MainScreen(
                onOpenSettings = { navController.navigate(Routes.Settings) },
                onRequestPermissions = onRequestPermissions
            )
        }
        composable(Routes.Settings) {
            EmergencyScreenSettings(
//                onBack = { navController.popBackStack() }
            )
        }
    }
}

