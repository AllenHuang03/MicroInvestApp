package com.microinvest.app.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microinvest.app.ui.screens.dashboard.DashboardScreen
import com.microinvest.app.ui.screens.portfolio.PortfolioScreen
import com.microinvest.app.ui.screens.budget.BudgetScreen
import com.microinvest.app.ui.screens.education.EducationScreen
import com.microinvest.app.ui.screens.profile.ProfileScreen
import com.microinvest.app.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("dashboard") {
                DashboardScreen()
            }
            composable("portfolio") {
                PortfolioScreen()
            }
            composable("budget") {
                BudgetScreen()
            }
            composable("education") {
                EducationScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
        }
    }
}