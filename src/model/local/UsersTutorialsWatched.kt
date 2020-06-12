package com.vladtruta.model.local

data class UsersTutorialsWatched(
    val userId: String,
    val tutorialId: Int,
    val watchCount: Int,
    val rating: Double
)