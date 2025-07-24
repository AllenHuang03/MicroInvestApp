package com.microinvest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: Long,
    val category: String, // e.g., "Food", "Transportation", "Entertainment"
    val budgetAmount: Double, // Monthly budget limit
    val spentAmount: Double = 0.0, // Amount spent so far
    val month: Int, // 1-12
    val year: Int,
    val createdAt: Long = System.currentTimeMillis()
) {
    val remainingAmount: Double
        get() = budgetAmount - spentAmount
        
    val spentPercentage: Double
        get() = if (budgetAmount > 0) (spentAmount / budgetAmount) * 100 else 0.0
        
    val isOverBudget: Boolean
        get() = spentAmount > budgetAmount
}