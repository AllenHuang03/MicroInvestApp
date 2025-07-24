package com.microinvest.app.ui.screens.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microinvest.app.domain.usecase.GetPortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()
    
    fun loadPortfolio() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getPortfolioUseCase().collect { portfolio ->
                val investments = portfolio.investments.groupBy { it.symbol }.map { (symbol, investments) ->
                    val totalShares = investments.sumOf { it.shares.toDouble() }
                    val totalInvested = investments.sumOf { it.amount.toDouble() }
                    val avgCost = totalInvested / totalShares
                    val currentPrice = investments.first().pricePerShare.toDouble() // This should be updated with real-time price
                    val currentValue = totalShares * currentPrice
                    val gainLoss = currentValue - totalInvested
                    
                    PortfolioInvestment(
                        symbol = symbol,
                        shares = totalShares,
                        avgCost = avgCost,
                        currentPrice = currentPrice,
                        currentValue = currentValue,
                        totalInvested = totalInvested,
                        gainLoss = gainLoss
                    )
                }
                
                _uiState.value = _uiState.value.copy(
                    totalValue = portfolio.totalValue.toDouble(),
                    totalInvested = portfolio.totalInvested.toDouble(),
                    totalGain = portfolio.totalGainLoss.toDouble(),
                    gainPercentage = portfolio.gainLossPercentage,
                    investments = investments,
                    isLoading = false
                )
            }
        }
    }
}

data class PortfolioUiState(
    val isLoading: Boolean = false,
    val totalValue: Double = 0.0,
    val totalInvested: Double = 0.0,
    val totalGain: Double = 0.0,
    val gainPercentage: Double = 0.0,
    val investments: List<PortfolioInvestment> = emptyList(),
    val errorMessage: String? = null
)

data class PortfolioInvestment(
    val symbol: String,
    val shares: Double,
    val avgCost: Double,
    val currentPrice: Double,
    val currentValue: Double,
    val totalInvested: Double,
    val gainLoss: Double
)