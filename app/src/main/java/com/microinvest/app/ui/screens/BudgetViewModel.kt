package com.microinvest.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microinvest.app.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    
    private val currentUserId = 1L // In a real app, get from auth/session
    
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()
    
    init {
        loadBudgets()
    }
    
    private fun loadBudgets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Load budget categories
                val budgetCategories = budgetRepository.getUserBudgetCategories(currentUserId)
                val budgetCategoryUiModels = budgetCategories.map { budget ->
                    val spentAmount = budgetRepository.getCategorySpentAmount(currentUserId, budget.category)
                    val remainingAmount = budget.budgetAmount - spentAmount
                    val progress = if (budget.budgetAmount > 0) (spentAmount / budget.budgetAmount).toFloat() else 0f
                    
                    BudgetCategoryUiModel(
                        id = budget.id,
                        categoryName = budget.category,
                        budgetAmount = budget.budgetAmount,
                        spentAmount = spentAmount,
                        remainingAmount = remainingAmount,
                        progress = progress
                    )
                }
                
                // Calculate totals
                val totalBudget = budgetCategoryUiModels.sumOf { it.budgetAmount }
                val totalSpent = budgetCategoryUiModels.sumOf { it.spentAmount }
                val remainingBudget = totalBudget - totalSpent
                val budgetProgress = if (totalBudget > 0) (totalSpent / totalBudget).toFloat() else 0f
                
                _uiState.update { currentState ->
                    currentState.copy(
                        budgetCategories = budgetCategoryUiModels,
                        totalMonthlyBudget = totalBudget,
                        totalMonthlySpent = totalSpent,
                        remainingBudget = remainingBudget,
                        budgetProgress = budgetProgress,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Failed to load budgets: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun showAddBudgetDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }
    
    fun hideAddBudgetDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }
    
    fun addBudget(category: String, amount: Double) {
        viewModelScope.launch {
            try {
                budgetRepository.addBudgetCategory(
                    userId = currentUserId,
                    category = category,
                    budgetAmount = amount
                )
                
                hideAddBudgetDialog()
                loadBudgets() // Refresh the list
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to add budget: ${e.message}") 
                }
            }
        }
    }
    
    fun editBudget(budgetCategory: BudgetCategoryUiModel) {
        // TODO: Implement edit functionality
        // You can add an edit dialog similar to the add dialog
    }
    
    fun deleteBudget(budgetCategory: BudgetCategoryUiModel) {
        viewModelScope.launch {
            try {
                budgetRepository.deleteBudgetCategory(budgetCategory.id)
                loadBudgets() // Refresh the list
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to delete budget: ${e.message}") 
                }
            }
        }
    }
    
    fun refreshBudgets() {
        loadBudgets()
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class BudgetUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val budgetCategories: List<BudgetCategoryUiModel> = emptyList(),
    val totalMonthlyBudget: Double = 0.0,
    val totalMonthlySpent: Double = 0.0,
    val remainingBudget: Double = 0.0,
    val budgetProgress: Float = 0f,
    val showAddDialog: Boolean = false
)

data class BudgetCategoryUiModel(
    val id: Long,
    val categoryName: String,
    val budgetAmount: Double,
    val spentAmount: Double,
    val remainingAmount: Double,
    val progress: Float
)