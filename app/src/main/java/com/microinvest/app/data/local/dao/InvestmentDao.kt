package com.microinvest.app.data.local.dao

import androidx.room.*
import com.microinvest.app.data.local.entity.Investment
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestmentDao {
    
    @Query("SELECT * FROM investments WHERE userId = :userId ORDER BY purchaseDate DESC")
    fun getInvestmentsByUser(userId: Long): Flow<List<Investment>>
    
    @Query("SELECT * FROM investments WHERE id = :investmentId")
    suspend fun getInvestmentById(investmentId: Long): Investment?
    
    @Query("SELECT * FROM investments WHERE userId = :userId AND symbol = :symbol")
    suspend fun getInvestmentsBySymbol(userId: Long, symbol: String): List<Investment>
    
    @Query("SELECT SUM(shares * currentPrice) FROM investments WHERE userId = :userId")
    suspend fun getTotalPortfolioValue(userId: Long): Double?
    
    @Query("SELECT SUM(shares * purchasePrice) FROM investments WHERE userId = :userId")  
    suspend fun getTotalInvestmentCost(userId: Long): Double?
    
    @Query("SELECT DISTINCT symbol FROM investments WHERE userId = :userId ORDER BY symbol")
    suspend fun getUniqueSymbols(userId: Long): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvestment(investment: Investment): Long
    
    @Update
    suspend fun updateInvestment(investment: Investment)
    
    @Delete
    suspend fun deleteInvestment(investment: Investment)
    
    @Query("DELETE FROM investments WHERE id = :investmentId")
    suspend fun deleteInvestmentById(investmentId: Long)
    
    @Query("UPDATE investments SET currentPrice = :newPrice WHERE symbol = :symbol")
    suspend fun updatePriceForSymbol(symbol: String, newPrice: Double)
}