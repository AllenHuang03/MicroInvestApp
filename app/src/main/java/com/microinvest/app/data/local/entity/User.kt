package com.microinvest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdAt: Long = System.currentTimeMillis()
)