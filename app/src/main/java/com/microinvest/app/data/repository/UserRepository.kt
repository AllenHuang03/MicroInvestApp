package com.microinvest.app.data.repository

import com.microinvest.app.data.local.dao.UserDao
import com.microinvest.app.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    // Get all users
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    
    // Get user by ID
    suspend fun getUserById(userId: Long): User? = userDao.getUserById(userId)
    
    // Get user by email (for login)
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    
    // Create new user
    suspend fun createUser(user: User): Long = userDao.insertUser(user)
    
    // Create user with individual parameters
    suspend fun createUser(
        email: String,
        firstName: String,
        lastName: String
    ): Long {
        val user = User(
            email = email,
            firstName = firstName,
            lastName = lastName
        )
        return userDao.insertUser(user)
    }
    
    // Update user
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    // Delete user
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    
    // Delete user by ID
    suspend fun deleteUserById(userId: Long) = userDao.deleteUserById(userId)
    
    // Check if user exists by email
    suspend fun userExistsByEmail(email: String): Boolean {
        return getUserByEmail(email) != null
    }
    
    // Get user's full name
    suspend fun getUserFullName(userId: Long): String {
        val user = getUserById(userId)
        return user?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown User"
    }
}