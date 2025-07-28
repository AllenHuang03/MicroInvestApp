package com.microinvest.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microinvest.app.data.repository.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentViewModel @Inject constructor(
    private val investmentRepository: InvestmentRepository
) : ViewModel() {
    
    private val currentUserId = 1L // In a real app, get from auth/session
    
    private val _uiState = MutableStateFlow(InvestmentUiState())
    val uiState: StateFlow<InvestmentUiState> = _uiState.asStateFlow()
    
    init {
        loadInvestments()
    }
    
    private fun loadInvestments() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Collect from Flow
                investmentRepository.getUserInvestments(currentUserId).collect { investments ->
                    val investmentUiModels = investments.map { investment ->
                        InvestmentUiModel(
                            id = investment.id,
                            symbol = investment.symbol,
                            name = investment.name,
                            shares = investment.shares,
                            avgCost = investment.purchasePrice, // Use purchasePrice from entity
                            currentPrice = investment.currentPrice,
                            totalValue = investment.shares * investment.currentPrice,
                            gainLoss = (investment.currentPrice - investment.purchasePrice) * investment.shares,
                            gainLossPercentage = if (investment.purchasePrice > 0) 
                                ((investment.currentPrice - investment.purchasePrice) / investment.purchasePrice) * 100 
                                else 0.0
                        )
                    }
                    
                    // Calculate portfolio totals
                    val totalValue = investmentUiModels.sumOf { it.totalValue }
                    val totalCost = investmentUiModels.sumOf { it.shares * it.avgCost }
                    val totalGainLoss = totalValue - totalCost
                    val gainLossPercentage = if (totalCost > 0) (totalGainLoss / totalCost) * 100 else 0.0
                    
                    _uiState.update { currentState ->
                        currentState.copy(
                            investments = investmentUiModels,
                            totalPortfolioValue = totalValue,
                            totalCost = totalCost,
                            totalGainLoss = totalGainLoss,
                            gainLossPercentage = gainLossPercentage,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Failed to load investments: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun showAddInvestmentDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }
    
    fun hideAddInvestmentDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }
    
    fun addInvestment(symbol: String, name: String, shares: Double, price: Double) {
        viewModelScope.launch {
            try {
                investmentRepository.addInvestment(
                    userId = currentUserId,
                    symbol = symbol,
                    name = name,
                    shares = shares,
                    avgCost = price, // This maps to purchasePrice in repository
                    currentPrice = price // In real app, fetch current price from API
                )
                
                hideAddInvestmentDialog()
                // loadInvestments() will be called automatically due to Flow collection
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to add investment: ${e.message}") 
                }
            }
        }
    }
    
    fun editInvestment(investment: InvestmentUiModel) {
        // TODO: Implement edit functionality
        // You can add an edit dialog similar to the add dialog
    }
    
    fun deleteInvestment(investment: InvestmentUiModel) {
        viewModelScope.launch {
            try {
                investmentRepository.deleteInvestment(investment.id)
                // loadInvestments() will be called automatically due to Flow collection
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to delete investment: ${e.message}") 
                }
            }
        }
    }
    
    fun refreshInvestments() {
        // No need to manually refresh - Flow will update automatically
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class InvestmentUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val investments: List<InvestmentUiModel> = emptyList(),
    val totalPortfolioValue: Double = 0.0,
    val totalCost: Double = 0.0,
    val totalGainLoss: Double = 0.0,
    val gainLossPercentage: Double = 0.0,
    val showAddDialog: Boolean = false
)

data class InvestmentUiModel(
    val id: Long,
    val symbol: String,
    val name: String,
    val shares: Double,
    val avgCost: Double,
    val currentPrice: Double,
    val totalValue: Double,
    val gainLoss: Double,
    val gainLossPercentage: Double
)