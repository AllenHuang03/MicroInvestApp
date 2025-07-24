package com.microinvest.app.domain.model

data class Investment(
    val id: String,
    val name: String,
    val symbol: String,
    val amount: Double,
    val currentPrice: Double,
    val purchasePrice: Double,
    val quantity: Double,
    val purchaseDate: Long,
    val type: InvestmentType
)

enum class InvestmentType {
    STOCK,
    ETF,
    CRYPTO,
    BOND
}

data class Portfolio(
    val totalValue: Double,
    val totalGainLoss: Double,
    val totalGainLossPercentage: Double,
    val investments: List<Investment>
)

data class SpareChangeTransaction(
    val id: String,
    val amount: Double,
    val roundedAmount: Double,
    val spareChange: Double,
    val date: Long,
    val merchantName: String
)