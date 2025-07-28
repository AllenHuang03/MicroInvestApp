package com.microinvest.app.data.repository

import com.microinvest.app.data.local.dao.BudgetDao
import com.microinvest.app.data.local.dao.ExpenseDao
import com.microinvest.app.data.local.entity.Budget
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao,
    private val expenseDao: ExpenseDao
) {
    
    // ========== UI Expected Methods ==========
    // These methods match what your UI ViewModels expect
    
    fun getUserBudgetCategories(userId: Long): Flow<List<Budget>> = 
        budgetDao.getBudgetsByUser(userId)
    
    suspend fun getCategorySpentAmount(userId: Long, category: String): Double {
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
        
        return expenseDao.getTotalExpensesByCategoryInRange(userId, category, startOfMonth, endOfMonth) ?: 0.0
    }
    
    suspend fun addBudgetCategory(
        userId: Long,
        category: String,
        budgetAmount: Double
    ): Long {
        return createBudget(userId, category, budgetAmount)
    }
    
    suspend fun deleteBudgetCategory(budgetId: Long) {
        deleteBudgetById(budgetId)
    }
    
    // ========== Original Methods (Keep All) ==========
    
    // Get all budgets for a user
    fun getBudgetsByUser(userId: Long): Flow<List<Budget>> = 
        budgetDao.getBudgetsByUser(userId)
    
    // Get budgets for specific month
    fun getBudgetsForMonth(userId: Long, month: Int, year: Int): Flow<List<Budget>> = 
        budgetDao.getBudgetsForMonth(userId, month, year)
    
    // Get budgets for current month
    fun getCurrentMonthBudgets(userId: Long): Flow<List<Budget>> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
        val currentYear = calendar.get(Calendar.YEAR)
        return getBudgetsForMonth(userId, currentMonth, currentYear)
    }
    
    // Get budget by ID
    suspend fun getBudgetById(budgetId: Long): Budget? = 
        budgetDao.getBudgetById(budgetId)
    
    // Get budget by category for specific month
    suspend fun getBudgetByCategory(
        userId: Long, 
        category: String, 
        month: Int, 
        year: Int
    ): Budget? = budgetDao.getBudgetByCategory(userId, category, month, year)
    
    // Create new budget
    suspend fun createBudget(budget: Budget): Long = 
        budgetDao.insertBudget(budget)
    
    // Create budget with parameters
    suspend fun createBudget(
        userId: Long,
        category: String,
        budgetAmount: Double,
        month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
        year: Int = Calendar.getInstance().get(Calendar.YEAR)
    ): Long {
        val budget = Budget(
            userId = userId,
            category = category,
            budgetAmount = budgetAmount,
            month = month,
            year = year
        )
        return budgetDao.insertBudget(budget)
    }
    
    // Update budget
    suspend fun updateBudget(budget: Budget) = 
        budgetDao.updateBudget(budget)
    
    // Delete budget
    suspend fun deleteBudget(budget: Budget) = 
        budgetDao.deleteBudget(budget)
    
    // Delete budget by ID
    suspend fun deleteBudgetById(budgetId: Long) = 
        budgetDao.deleteBudgetById(budgetId)
    
    // Add to spent amount
    suspend fun addToSpentAmount(budgetId: Long, amount: Double) = 
        budgetDao.addToSpentAmount(budgetId, amount)
    
    // Budget summary methods
    suspend fun getTotalBudgetForMonth(userId: Long, month: Int, year: Int): Double = 
        budgetDao.getTotalBudgetForMonth(userId, month, year) ?: 0.0
    
    suspend fun getTotalSpentForMonth(userId: Long, month: Int, year: Int): Double = 
        budgetDao.getTotalSpentForMonth(userId, month, year) ?: 0.0
    
    suspend fun getCurrentMonthTotalBudget(userId: Long): Double {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        return getTotalBudgetForMonth(userId, currentMonth, currentYear)
    }
    
    suspend fun getCurrentMonthTotalSpent(userId: Long): Double {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        return getTotalSpentForMonth(userId, currentMonth, currentYear)
    }
    
    suspend fun getCurrentMonthRemainingBudget(userId: Long): Double {
        return getCurrentMonthTotalBudget(userId) - getCurrentMonthTotalSpent(userId)
    }
    
    // Get unique categories
    suspend fun getUniqueCategories(userId: Long): List<String> = 
        budgetDao.getUniqueCategories(userId)
    
    // Check if budget exists for category and month
    suspend fun budgetExistsForCategory(
        userId: Long, 
        category: String, 
        month: Int, 
        year: Int
    ): Boolean {
        return getBudgetByCategory(userId, category, month, year) != null
    }
    
    // Get over-budget categories for current month
    suspend fun getOverBudgetCategories(userId: Long): List<Budget> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        
        // Note: This would need to be implemented as a Flow and collected
        // For now, returning empty list - you'd need to collect the Flow first
        return emptyList()
    }
}