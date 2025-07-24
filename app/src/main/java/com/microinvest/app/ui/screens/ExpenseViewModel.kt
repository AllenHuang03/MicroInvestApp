package com.microinvest.app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microinvest.app.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    
    private val currentUserId = 1L // In a real app, get from auth/session
    
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()
    
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    init {
        loadExpenses()
    }
    
    private fun loadExpenses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                // Load expenses
                val expenses = expenseRepository.getUserExpenses(currentUserId)
                val expenseUiModels = expenses.map { expense ->
                    ExpenseUiModel(
                        id = expense.id,
                        description = expense.description,
                        amount = expense.amount,
                        category = expense.category,
                        date = expense.date,
                        formattedDate = dateFormatter.format(expense.date)
                    )
                }.sortedByDescending { it.date } // Sort by date, newest first
                
                // Calculate monthly total
                val calendar = Calendar.getInstance()
                val currentMonth = calendar.get(Calendar.MONTH)
                val currentYear = calendar.get(Calendar.YEAR)
                
                val monthlyExpenses = expenseUiModels.filter { expense ->
                    val expenseCalendar = Calendar.getInstance().apply { time = expense.date }
                    expenseCalendar.get(Calendar.MONTH) == currentMonth && 
                    expenseCalendar.get(Calendar.YEAR) == currentYear
                }
                
                val totalMonthlyExpenses = monthlyExpenses.sumOf { it.amount }
                
                // Group expenses by category for current month
                val expensesByCategory = monthlyExpenses
                    .groupBy { it.category }
                    .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
                    .toList()
                    .sortedByDescending { it.second }
                    .toMap()
                
                _uiState.update { currentState ->
                    currentState.copy(
                        expenses = expenseUiModels,
                        totalMonthlyExpenses = totalMonthlyExpenses,
                        expensesByCategory = expensesByCategory,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Failed to load expenses: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun showAddExpenseDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }
    
    fun hideAddExpenseDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }
    
    fun addExpense(description: String, amount: Double, category: String) {
        viewModelScope.launch {
            try {
                expenseRepository.addExpense(
                    userId = currentUserId,
                    description = description,
                    amount = amount,
                    category = category,
                    date = Date() // Current date
                )
                
                hideAddExpenseDialog()
                loadExpenses() // Refresh the list
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to add expense: ${e.message}") 
                }
            }
        }
    }
    
    fun editExpense(expense: ExpenseUiModel) {
        // TODO: Implement edit functionality
        // You can add an edit dialog similar to the add dialog
    }
    
    fun deleteExpense(expense: ExpenseUiModel) {
        viewModelScope.launch {
            try {
                expenseRepository.deleteExpense(expense.id)
                loadExpenses() // Refresh the list
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to delete expense: ${e.message}") 
                }
            }
        }
    }
    
    fun refreshExpenses() {
        loadExpenses()
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class ExpenseUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val expenses: List<ExpenseUiModel> = emptyList(),
    val totalMonthlyExpenses: Double = 0.0,
    val expensesByCategory: Map<String, Double> = emptyMap(),
    val showAddDialog: Boolean = false
)

data class ExpenseUiModel(
    val id: Long,
    val description: String,
    val amount: Double,
    val category: String,
    val date: Date,
    val formattedDate: String
)