package com.microinvest.app.di

import android.content.Context
import androidx.room.Room
import com.microinvest.app.data.local.AppDatabase
import com.microinvest.app.data.local.dao.BudgetDao
import com.microinvest.app.data.local.dao.ExpenseDao
import com.microinvest.app.data.local.dao.InvestmentDao
import com.microinvest.app.data.local.dao.UserDao
import com.microinvest.app.data.repository.BudgetRepository
import com.microinvest.app.data.repository.ExpenseRepository
import com.microinvest.app.data.repository.InvestmentRepository
import com.microinvest.app.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    
    // DAO Providers
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
    
    @Provides
    fun provideInvestmentDao(database: AppDatabase): InvestmentDao {
        return database.investmentDao()
    }
    
    @Provides
    fun provideBudgetDao(database: AppDatabase): BudgetDao {
        return database.budgetDao()
    }
    
    @Provides
    fun provideExpenseDao(database: AppDatabase): ExpenseDao {
        return database.expenseDao()
    }
    
    // Repository Providers (automatically injected by @Inject constructor)
    // No need to provide these manually since they use @Inject constructor
}