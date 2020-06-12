package com.vladtruta.model.local

data class UsersTutorialsWatched(
    val userIdToken: String,
    val tutorialId: Int,
    val watchCount: Int,
    val rating: Double
)