package com.microinvest.app.data.local.dao

import androidx.room.*
import com.microinvest.app.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    
    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getExpensesByUser(userId: Long): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: Long): Expense?
    
    @Query("SELECT * FROM expenses WHERE userId = :userId AND category = :category ORDER BY date DESC")
    fun getExpensesByCategory(userId: Long, category: String): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getExpensesInDateRange(userId: Long, startDate: Long, endDate: Long): Flow<List<Expense>>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalExpensesInRange(userId: Long, startDate: Long, endDate: Long): Double?
    
    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId AND category = :category AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalExpensesByCategoryInRange(userId: Long, category: String, startDate: Long, endDate: Long): Double?
    
    @Query("SELECT DISTINCT category FROM expenses WHERE userId = :userId ORDER BY category")
    suspend fun getUniqueCategories(userId: Long): List<String>
    
    @Query("SELECT * FROM expenses WHERE budgetId = :budgetId ORDER BY date DESC")
    fun getExpensesByBudget(budgetId: Long): Flow<List<Expense>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long
    
    @Update
    suspend fun updateExpense(expense: Expense)
    
    @Delete
    suspend fun deleteExpense(expense: Expense)
    
    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Long)
}