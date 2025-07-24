package com.microinvest.app.domain.model

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
    val isEmailVerified: Boolean = false,
    val subscriptionType: SubscriptionType = SubscriptionType.FREE,
    val createdAt: Long,
    val updatedAt: Long
)

enum class SubscriptionType {
    FREE,
    PREMIUM,
    WEALTH_MANAGEMENT
}