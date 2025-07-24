package com.microinvest.app.domain.model

import java.math.BigDecimal

data class Subscription(
    val id: String,
    val userId: String,
    val type: SubscriptionType,
    val status: SubscriptionStatus,
    val startDate: Long,
    val endDate: Long?,
    val autoRenew: Boolean = true,
    val paymentMethodId: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)

enum class SubscriptionStatus {
    ACTIVE,
    CANCELLED,
    EXPIRED,
    PENDING,
    TRIAL
}

data class SubscriptionPlan(
    val type: SubscriptionType,
    val name: String,
    val description: String,
    val monthlyPrice: BigDecimal,
    val yearlyPrice: BigDecimal,
    val features: List<PremiumFeature>,
    val isPopular: Boolean = false
)

enum class PremiumFeature {
    UNLIMITED_BUDGETS,
    ADVANCED_ANALYTICS,
    CRYPTO_INVESTING,
    WEALTH_MANAGEMENT,
    PRIORITY_SUPPORT,
    CUSTOM_PORTFOLIOS,
    TAX_OPTIMIZATION,
    FINANCIAL_ADVISOR_ACCESS,
    REAL_TIME_MARKET_DATA,
    ADVANCED_EDUCATION_CONTENT
}

data class PaymentMethod(
    val id: String,
    val userId: String,
    val type: PaymentType,
    val last4Digits: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val isDefault: Boolean = false,
    val createdAt: Long
)

enum class PaymentType {
    CREDIT_CARD,
    DEBIT_CARD,
    BANK_ACCOUNT,
    DIGITAL_WALLET
}

data class Transaction(
    val id: String,
    val userId: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val status: TransactionStatus,
    val description: String,
    val paymentMethodId: String? = null,
    val subscriptionId: String? = null,
    val timestamp: Long
)

enum class TransactionType {
    SUBSCRIPTION_PAYMENT,
    INVESTMENT,
    REFUND,
    FEE
}

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED,
    REFUNDED
}