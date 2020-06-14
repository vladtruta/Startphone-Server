package com.vladtruta.model.local

data class UsersTutorialsRated(
    val userId: String,
    val tutorialId: Int,
    val rating: Double
)