package com.microinvest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: Long,
    val amount: Double,
    val category: String, // e.g., "Food", "Transportation", "Entertainment"
    val description: String,
    val date: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false,
    val budgetId: Long? = null // Link to budget if applicable
)