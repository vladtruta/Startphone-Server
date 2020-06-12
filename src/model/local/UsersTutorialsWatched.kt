package com.vladtruta.model.local

data class UsersTutorialsWatched(
    val userEmail: String,
    val tutorialId: Int,
    val watchCount: Int,
    val rating: Double
)