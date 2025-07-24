package com.microinvest.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.microinvest.app.data.local.dao.BudgetDao
import com.microinvest.app.data.local.dao.ExpenseDao
import com.microinvest.app.data.local.dao.InvestmentDao
import com.microinvest.app.data.local.dao.UserDao
import com.microinvest.app.data.local.entity.Budget
import com.microinvest.app.data.local.entity.Expense
import com.microinvest.app.data.local.entity.Investment
import com.microinvest.app.data.local.entity.User

@Database(
    entities = [
        User::class,
        Investment::class,
        Budget::class,
        Expense::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao
    
    companion object {
        const val DATABASE_NAME = "microinvest_database"
    }
}