package com.microinvest.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    "Welcome, ${uiState.userName}",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            actions = {
                IconButton(onClick = { /* Open settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Portfolio Summary Card
            item {
                PortfolioSummaryCard(
                    totalValue = uiState.portfolioValue,
                    totalGainLoss = uiState.portfolioGainLoss,
                    gainLossPercentage = uiState.portfolioGainLossPercentage
                )
            }
            
            // Quick Actions
            item {
                QuickActionsSection()
            }
            
            // Budget Overview
            item {
                BudgetOverviewCard(
                    totalBudget = uiState.monthlyBudget,
                    totalSpent = uiState.monthlySpent,
                    remainingBudget = uiState.remainingBudget
                )
            }
            
            // Recent Transactions
            item {
                RecentTransactionsCard(
                    recentExpenses = uiState.recentExpenses
                )
            }
            
            // Investment Performance
            item {
                TopInvestmentsCard(
                    topInvestments = uiState.topPerformingInvestments
                )
            }
        }
    }
}

@Composable
fun PortfolioSummaryCard(
    totalValue: Double,
    totalGainLoss: Double,
    gainLossPercentage: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Portfolio Value",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Icon(
                    Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "$${String.format("%.2f", totalValue)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val gainLossColor = if (totalGainLoss >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                val gainLossIcon = if (totalGainLoss >= 0) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
                
                Icon(
                    gainLossIcon,
                    contentDescription = null,
                    tint = gainLossColor,
                    modifier = Modifier.size(16.dp)
                )
                
                Text(
                    text = "${if (totalGainLoss >= 0) "+" else ""}$${String.format("%.2f", totalGainLoss)} (${String.format("%.1f", gainLossPercentage)}%)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = gainLossColor,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun QuickActionsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(quickActions) { action ->
                    QuickActionButton(
                        icon = action.icon,
                        label = action.label,
                        onClick = action.onClick
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onClick() }
            .padding(16.dp)
            .width(80.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun BudgetOverviewCard(
    totalBudget: Double,
    totalSpent: Double,
    remainingBudget: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "This Month's Budget",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    Icons.Default.AccountBalanceWallet,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Budget Progress Bar
            val progress = if (totalBudget > 0) (totalSpent / totalBudget).toFloat() else 0f
            
            LinearProgressIndicator(
                progress = progress.coerceAtMost(1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (progress > 0.8f) Color(0xFFF44336) 
                       else if (progress > 0.6f) Color(0xFFFF9800) 
                       else Color(0xFF4CAF50)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Spent",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${String.format("%.0f", totalSpent)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Remaining",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${String.format("%.0f", remainingBudget)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = if (remainingBudget < 0) Color(0xFFF44336) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun RecentTransactionsCard(
    recentExpenses: List<String> // Simplified for demo
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (recentExpenses.isEmpty()) {
                Text(
                    text = "No recent transactions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                recentExpenses.take(3).forEach { expense ->
                    TransactionItem(expense)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            TextButton(
                onClick = { /* Navigate to expenses */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("View All")
            }
        }
    }
}

@Composable
fun TransactionItem(expense: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = expense,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = "-$25.99", // Demo amount
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFF44336)
        )
    }
}

@Composable
fun TopInvestmentsCard(
    topInvestments: List<String> // Simplified for demo
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Top Performing Investments",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (topInvestments.isEmpty()) {
                Text(
                    text = "No investments yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                topInvestments.take(3).forEach { investment ->
                    InvestmentItem(investment)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            TextButton(
                onClick = { /* Navigate to investments */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("View Portfolio")
            }
        }
    }
}

@Composable
fun InvestmentItem(investment: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.TrendingUp,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF4CAF50)
            )
            Text(
                text = investment,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = "+5.2%", // Demo percentage
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF4CAF50)
        )
    }
}

// Demo data - replace with actual data from ViewModel
data class QuickAction(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)

val quickActions = listOf(
    QuickAction(Icons.Default.Add, "Add Expense") { },
    QuickAction(Icons.Default.TrendingUp, "Buy Stock") { },
    QuickAction(Icons.Default.AccountBalance, "Set Budget") { },
    QuickAction(Icons.Default.Analytics, "View Reports") { }
)