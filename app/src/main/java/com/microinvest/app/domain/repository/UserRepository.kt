package com.microinvest.app.domain.repository

import com.microinvest.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteAccount(): Result<Unit>
    fun observeUser(): Flow<User?>
}