package com.microinvest.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microinvest.app.data.repository.BudgetRepository
import com.microinvest.app.data.repository.ExpenseRepository
import com.microinvest.app.data.repository.InvestmentRepository
import com.microinvest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val investmentRepository: InvestmentRepository,
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    
    // For demo purposes, using a fixed user ID
    // In a real app, you'd get this from authentication/session management
    private val currentUserId = 1L
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            // Load user info
            loadUserInfo()
            
            // Load portfolio data
            loadPortfolioData()
            
            // Load budget data
            loadBudgetData()
            
            // Load recent expenses
            loadRecentExpenses()
            
            // Load top investments
            loadTopInvestments()
        }
    }
    
    private suspend fun loadUserInfo() {
        try {
            val user = userRepository.getUserById(currentUserId)
            _uiState.update { currentState ->
                currentState.copy(
                    userName = user?.firstName ?: "User",
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, error = "Failed to load user info") }
        }
    }
    
    private suspend fun loadPortfolioData() {
        try {
            val totalValue = investmentRepository.getTotalPortfolioValue(currentUserId)
            val totalCost = investmentRepository.getTotalInvestmentCost(currentUserId)
            val gainLoss = investmentRepository.getTotalGainLoss(currentUserId)
            val gainLossPercentage = investmentRepository.getGainLossPercentage(currentUserId)
            
            _uiState.update { currentState ->
                currentState.copy(
                    portfolioValue = totalValue,
                    portfolioGainLoss = gainLoss,
                    portfolioGainLossPercentage = gainLossPercentage
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to load portfolio data") }
        }
    }
    
    private suspend fun loadBudgetData() {
        try {
            val totalBudget = budgetRepository.getCurrentMonthTotalBudget(currentUserId)
            val totalSpent = budgetRepository.getCurrentMonthTotalSpent(currentUserId)
            val remaining = budgetRepository.getCurrentMonthRemainingBudget(currentUserId)
            
            _uiState.update { currentState ->
                currentState.copy(
                    monthlyBudget = totalBudget,
                    monthlySpent = totalSpent,
                    remainingBudget = remaining
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to load budget data") }
        }
    }
    
    private suspend fun loadRecentExpenses() {
        try {
            // In a real implementation, you'd collect from the Flow
            // For now, using demo data
            val recentExpenses = listOf(
                "Grocery Store",
                "Gas Station", 
                "Coffee Shop"
            )
            
            _uiState.update { currentState ->
                currentState.copy(recentExpenses = recentExpenses)
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to load recent expenses") }
        }
    }
    
    private suspend fun loadTopInvestments() {
        try {
            // In a real implementation, you'd process investment data
            // For now, using demo data
            val topInvestments = listOf(
                "AAPL",
                "GOOGL",
                "TSLA"
            )
            
            _uiState.update { currentState ->
                currentState.copy(topPerformingInvestments = topInvestments)
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to load top investments") }
        }
    }
    
    fun refreshData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        loadDashboardData()
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class DashboardUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    
    // User info
    val userName: String = "",
    
    // Portfolio data
    val portfolioValue: Double = 0.0,
    val portfolioGainLoss: Double = 0.0,
    val portfolioGainLossPercentage: Double = 0.0,
    
    // Budget data
    val monthlyBudget: Double = 0.0,
    val monthlySpent: Double = 0.0,
    val remainingBudget: Double = 0.0,
    
    // Recent data
    val recentExpenses: List<String> = emptyList(),
    val topPerformingInvestments: List<String> = emptyList()
)