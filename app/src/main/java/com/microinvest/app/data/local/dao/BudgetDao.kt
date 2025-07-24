package com.microinvest.app.data.local.dao

import androidx.room.*
import com.microinvest.app.data.local.entity.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    
    @Query("SELECT * FROM budgets WHERE userId = :userId ORDER BY year DESC, month DESC")
    fun getBudgetsByUser(userId: Long): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE userId = :userId AND month = :month AND year = :year")
    fun getBudgetsForMonth(userId: Long, month: Int, year: Int): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE id = :budgetId")
    suspend fun getBudgetById(budgetId: Long): Budget?
    
    @Query("SELECT * FROM budgets WHERE userId = :userId AND category = :category AND month = :month AND year = :year")
    suspend fun getBudgetByCategory(userId: Long, category: String, month: Int, year: Int): Budget?
    
    @Query("SELECT SUM(budgetAmount) FROM budgets WHERE userId = :userId AND month = :month AND year = :year")
    suspend fun getTotalBudgetForMonth(userId: Long, month: Int, year: Int): Double?
    
    @Query("SELECT SUM(spentAmount) FROM budgets WHERE userId = :userId AND month = :month AND year = :year")
    suspend fun getTotalSpentForMonth(userId: Long, month: Int, year: Int): Double?
    
    @Query("SELECT DISTINCT category FROM budgets WHERE userId = :userId ORDER BY category")
    suspend fun getUniqueCategories(userId: Long): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget): Long
    
    @Update
    suspend fun updateBudget(budget: Budget)
    
    @Delete
    suspend fun deleteBudget(budget: Budget)
    
    @Query("DELETE FROM budgets WHERE id = :budgetId")
    suspend fun deleteBudgetById(budgetId: Long)
    
    @Query("UPDATE budgets SET spentAmount = spentAmount + :amount WHERE id = :budgetId")
    suspend fun addToSpentAmount(budgetId: Long, amount: Double)
}