package com.microinvest.app.domain.model

data class EducationalContent(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val category: EducationCategory,
    val difficulty: DifficultyLevel,
    val estimatedReadTime: Int, // in minutes
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val tags: List<String>,
    val isBookmarked: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)

enum class EducationCategory {
    BASICS,
    INVESTING,
    BUDGETING,
    CRYPTOCURRENCY,
    WEALTH_MANAGEMENT,
    RETIREMENT_PLANNING,
    TAX_PLANNING
}

enum class DifficultyLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

data class Quiz(
    val id: String,
    val title: String,
    val description: String,
    val category: EducationCategory,
    val questions: List<QuizQuestion>,
    val passingScore: Int, // percentage
    val estimatedTime: Int // in minutes
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class UserProgress(
    val userId: String,
    val contentId: String,
    val isCompleted: Boolean,
    val completionDate: Long? = null,
    val timeSpent: Int, // in minutes
    val quizScore: Int? = null
)