package com.microinvest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.microinvest.app.ui.theme.MicroInvestAppTheme
import com.microinvest.app.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MicroInvestAppTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                                label = { Text("Dashboard") },
                                selected = false,
                                onClick = { navController.navigate("dashboard") }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Receipt, contentDescription = "Expenses") },
                                label = { Text("Expenses") },
                                selected = false,
                                onClick = { navController.navigate("expenses") }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Budget") },
                                label = { Text("Budget") },
                                selected = false,
                                onClick = { navController.navigate("budget") }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.TrendingUp, contentDescription = "Investments") },
                                label = { Text("Investments") },
                                selected = false,
                                onClick = { navController.navigate("investments") }
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("dashboard") { DashboardScreen() }
                        composable("expenses") { ExpenseScreen() }
                        composable("budget") { BudgetScreen() }
                        composable("investments") { InvestmentScreen() }
                    }
                }
            }
        }
    }
}