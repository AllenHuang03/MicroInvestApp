package com.microinvest.app.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.microinvest.app.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        InvestmentEntity::class,
        SpareChangeTransactionEntity::class,
        BudgetEntity::class,
        ExpenseEntity::class,
        EducationalContentEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun budgetDao(): BudgetDao
    abstract fun educationDao(): EducationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "microinvest_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}