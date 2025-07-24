package com.microinvest.app.domain.model

data class Budget(
    val id: String,
    val category: String,
    val amount: Double,
    val spent: Double,
    val period: BudgetPeriod,
    val startDate: Long,
    val endDate: Long
) {
    val remainingAmount: Double
        get() = amount - spent
    
    val utilizationPercentage: Double
        get() = if (amount > 0) (spent / amount * 100) else 0.0
}

enum class BudgetPeriod {
    WEEKLY,
    MONTHLY,
    YEARLY
}

data class Expense(
    val id: String,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Long
)