package com.microinvest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "investments")
data class Investment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val userId: Long,
    val symbol: String, // Stock symbol (e.g., "AAPL", "GOOGL")
    val name: String,   // Company name
    val shares: Double, // Number of shares
    val purchasePrice: Double, // Price per share when purchased
    val currentPrice: Double = purchasePrice, // Current market price
    val purchaseDate: Long = System.currentTimeMillis(),
    val category: String = "Stock", // Stock, ETF, Crypto, etc.
    val notes: String = ""
) {
    val totalValue: Double
        get() = shares * currentPrice
    
    val totalCost: Double  
        get() = shares * purchasePrice
        
    val gainLoss: Double
        get() = totalValue - totalCost
        
    val gainLossPercentage: Double
        get() = if (totalCost > 0) (gainLoss / totalCost) * 100 else 0.0
}