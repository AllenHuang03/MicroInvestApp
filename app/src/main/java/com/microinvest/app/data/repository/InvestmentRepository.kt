package com.microinvest.app.data.repository

import com.microinvest.app.data.local.dao.InvestmentDao
import com.microinvest.app.data.local.entity.Investment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvestmentRepository @Inject constructor(
    private val investmentDao: InvestmentDao
) {
    
    // Get all investments for a user
    fun getInvestmentsByUser(userId: Long): Flow<List<Investment>> = 
        investmentDao.getInvestmentsByUser(userId)
    
    // Get investment by ID
    suspend fun getInvestmentById(investmentId: Long): Investment? = 
        investmentDao.getInvestmentById(investmentId)
    
    // Get investments by symbol
    suspend fun getInvestmentsBySymbol(userId: Long, symbol: String): List<Investment> = 
        investmentDao.getInvestmentsBySymbol(userId, symbol)
    
    // Add new investment
    suspend fun addInvestment(investment: Investment): Long = 
        investmentDao.insertInvestment(investment)
    
    // Add investment with parameters
    suspend fun addInvestment(
        userId: Long,
        symbol: String,
        name: String,
        shares: Double,
        purchasePrice: Double,
        category: String = "Stock",
        notes: String = ""
    ): Long {
        val investment = Investment(
            userId = userId,
            symbol = symbol,
            name = name,
            shares = shares,
            purchasePrice = purchasePrice,
            currentPrice = purchasePrice, // Initially same as purchase price
            category = category,
            notes = notes
        )
        return investmentDao.insertInvestment(investment)
    }
    
    // Update investment
    suspend fun updateInvestment(investment: Investment) = 
        investmentDao.updateInvestment(investment)
    
    // Update current price for a symbol
    suspend fun updatePriceForSymbol(symbol: String, newPrice: Double) = 
        investmentDao.updatePriceForSymbol(symbol, newPrice)
    
    // Delete investment
    suspend fun deleteInvestment(investment: Investment) = 
        investmentDao.deleteInvestment(investment)
    
    // Delete investment by ID
    suspend fun deleteInvestmentById(investmentId: Long) = 
        investmentDao.deleteInvestmentById(investmentId)
    
    // Get portfolio metrics
    suspend fun getTotalPortfolioValue(userId: Long): Double = 
        investmentDao.getTotalPortfolioValue(userId) ?: 0.0
    
    suspend fun getTotalInvestmentCost(userId: Long): Double = 
        investmentDao.getTotalInvestmentCost(userId) ?: 0.0
    
    suspend fun getTotalGainLoss(userId: Long): Double {
        val currentValue = getTotalPortfolioValue(userId)
        val totalCost = getTotalInvestmentCost(userId)
        return currentValue - totalCost
    }
    
    suspend fun getGainLossPercentage(userId: Long): Double {
        val totalCost = getTotalInvestmentCost(userId)
        return if (totalCost > 0) {
            (getTotalGainLoss(userId) / totalCost) * 100
        } else 0.0
    }
    
    // Get unique symbols for the user
    suspend fun getUniqueSymbols(userId: Long): List<String> = 
        investmentDao.getUniqueSymbols(userId)
    
    // Calculate total shares for a symbol
    suspend fun getTotalSharesForSymbol(userId: Long, symbol: String): Double {
        return getInvestmentsBySymbol(userId, symbol).sumOf { it.shares }
    }
    
    // Calculate average purchase price for a symbol
    suspend fun getAveragePurchasePriceForSymbol(userId: Long, symbol: String): Double {
        val investments = getInvestmentsBySymbol(userId, symbol)
        val totalCost = investments.sumOf { it.shares * it.purchasePrice }
        val totalShares = investments.sumOf { it.shares }
        return if (totalShares > 0) totalCost / totalShares else 0.0
    }
}