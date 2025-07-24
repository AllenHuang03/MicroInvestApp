package com.microinvest.app.domain.repository

import com.microinvest.app.domain.model.Investment
import com.microinvest.app.domain.model.Portfolio
import com.microinvest.app.domain.model.SpareChangeTransaction
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface InvestmentRepository {
    suspend fun investSpareChange(amount: BigDecimal, symbol: String): Result<Investment>
    suspend fun makeManualInvestment(amount: BigDecimal, symbol: String): Result<Investment>
    suspend fun getPortfolio(): Flow<Portfolio>
    suspend fun getInvestmentHistory(): Flow<List<Investment>>
    suspend fun getSpareChangeTransactions(): Flow<List<SpareChangeTransaction>>
    suspend fun processSpareChangeTransaction(transaction: SpareChangeTransaction): Result<Unit>
    suspend fun getMarketData(symbol: String): Result<MarketData>
}

data class MarketData(
    val symbol: String,
    val currentPrice: BigDecimal,
    val dayChange: BigDecimal,
    val dayChangePercent: Double,
    val volume: Long,
    val marketCap: BigDecimal? = null
)