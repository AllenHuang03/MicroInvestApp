package com.microinvest.app.data.repository

import com.microinvest.app.data.local.dao.ExpenseDao
import com.microinvest.app.data.local.dao.BudgetDao
import com.microinvest.app.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao
) {
    
    // ========== UI Expected Methods ==========
    // These methods match what your UI ViewModels expect
    
    fun getUserExpenses(userId: Long): Flow<List<Expense>> = 
        getExpensesByUser(userId)
    
    suspend fun addExpense(
        userId: Long,
        description: String,
        amount: Double,
        category: String,
        date: Date = Date()
    ): Long {
        val expense = Expense(
            userId = userId,
            description = description,
            amount = amount,
            category = category,
            date = date.time, // Convert Date to Long timestamp
            isRecurring = false,
            budgetId = null
        )
        return addExpense(expense)
    }
    
    suspend fun deleteExpense(expenseId: Long) {
        deleteExpenseById(expenseId)
    }
    
    // ========== Original Methods (Keep All) ==========
    
    // Get all expenses for a user
    fun getExpensesByUser(userId: Long): Flow<List<Expense>> = 
        expenseDao.getExpensesByUser(userId)
    
    // Get expense by ID
    suspend fun getExpenseById(expenseId: Long): Expense? = 
        expenseDao.getExpenseById(expenseId)
    
    // Get expenses by category
    fun getExpensesByCategory(userId: Long, category: String): Flow<List<Expense>> = 
        expenseDao.getExpensesByCategory(userId, category)
    
    // Get expenses in date range
    fun getExpensesInDateRange(
        userId: Long, 
        startDate: Long, 
        endDate: Long
    ): Flow<List<Expense>> = 
        expenseDao.getExpensesInDateRange(userId, startDate, endDate)
    
    // Get expenses for current month
    fun getCurrentMonthExpenses(userId: Long): Flow<List<Expense>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis
        
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val endOfMonth = calendar.timeInMillis
        
        return getExpensesInDateRange(userId, startOfMonth, endOfMonth)
    }
    
    // Add new expense (original method)
    suspend fun addExpense(expense: Expense): Long {
        val expenseId = expenseDao.insertExpense(expense)
        
        // Update budget spent amount if budget exists
        updateBudgetSpentAmount(expense)
        
        return expenseId
    }
    
    // Add expense with parameters (original method)
    suspend fun addExpense(
        userId: Long,
        amount: Double,
        category: String,
        description: String,
        date: Long = System.currentTimeMillis(),
        isRecurring: Boolean = false,
        budgetId: Long? = null
    ): Long {
        val expense = Expense(
            userId = userId,
            amount = amount,
            category = category,
            description = description,
            date = date,
            isRecurring = isRecurring,
            budgetId = budgetId
        )
        return addExpense(expense)
    }
    
    // Update expense
    suspend fun updateExpense(oldExpense: Expense, newExpense: Expense) {
        // Reverse the old expense's budget impact
        updateBudgetSpentAmount(oldExpense, reverse = true)
        
        // Apply the new expense
        expenseDao.updateExpense(newExpense)
        updateBudgetSpentAmount(newExpense)
    }
    
    // Delete expense (original method)
    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
        // Reverse the budget impact
        updateBudgetSpentAmount(expense, reverse = true)
    }
    
    // Delete expense by ID
    suspend fun deleteExpenseById(expenseId: Long) {
        val expense = getExpenseById(expenseId)
        expense?.let { deleteExpense(it) }
    }
    
    // Get total expenses in date range
    suspend fun getTotalExpensesInRange(
        userId: Long, 
        startDate: Long, 
        endDate: Long
    ): Double = expenseDao.getTotalExpensesInRange(userId, startDate, endDate) ?: 0.0
    
    // Get total expenses by category in date range
    suspend fun getTotalExpensesByCategoryInRange(
        userId: Long, 
        category: String, 
        startDate: Long, 
        endDate: Long
    ): Double = expenseDao.getTotalExpensesByCategoryInRange(userId, category, startDate, endDate) ?: 0.0
    
    // Get current month totals
    suspend fun getCurrentMonthTotal(userId: Long): Double {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis
        
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val endOfMonth = calendar.timeInMillis
        
        return getTotalExpensesInRange(userId, startOfMonth, endOfMonth)
    }
    
    // Get unique categories
    suspend fun getUniqueCategories(userId: Long): List<String> = 
        expenseDao.getUniqueCategories(userId)
    
    // Get expenses by budget
    fun getExpensesByBudget(budgetId: Long): Flow<List<Expense>> = 
        expenseDao.getExpensesByBudget(budgetId)
    
    // Private helper method to update budget spent amount
    private suspend fun updateBudgetSpentAmount(expense: Expense, reverse: Boolean = false) {
        // Find budget for this expense's category and month
        val calendar = Calendar.getInstance().apply { timeInMillis = expense.date }
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        
        val budget = budgetDao.getBudgetByCategory(
            expense.userId, 
            expense.category, 
            month, 
            year
        )
        
        budget?.let { 
            val amount = if (reverse) -expense.amount else expense.amount
            budgetDao.addToSpentAmount(it.id, amount)
        }
    }
    
    // Get daily spending for current month (for charts)
    suspend fun getDailySpendingCurrentMonth(userId: Long): Map<Int, Double> {
        // This would require a more complex query or processing the Flow
        // For now, returning empty map - implement based on your UI needs
        return emptyMap()
    }
    
    // Get category breakdown for current month
    suspend fun getCategoryBreakdownCurrentMonth(userId: Long): Map<String, Double> {
        // This would require processing the Flow
        // For now, returning empty map - implement based on your UI needs
        return emptyMap()
    }
}