package com.vladtruta.persistence

import com.vladtruta.model.local.*

interface IAppDao {
    fun getTutorial(id: Int): Tutorial?
    fun getTutorials(): List<Tutorial>
    fun insertTutorial(tutorial: Tutorial)
    fun updateTutorial(tutorial: Tutorial)
    fun deleteTutorial(id: Int)

    fun getUser(id: String): User?
    fun insertUser(user: User)
    fun updateUser(user: User)

    fun getWatchedTutorial(id: String, tutorialId: Int): UsersTutorialsWatched?
    fun insertWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched)
    fun updateWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched)

    fun getRatedTutorial(id: String, tutorialId: Int): UsersTutorialsRated?
    fun insertRatedTutorial(usersTutorialsRated: UsersTutorialsRated)
    fun updateRatedTutorial(usersTutorialsRated: UsersTutorialsRated)

    fun getTutorialMissing(id: String, packageName: String): UsersTutorialMissing?
    fun insertTutorialMissing(usersTutorialMissing: UsersTutorialMissing)
    fun updateTutorialMissing(usersTutorialMissing: UsersTutorialMissing)

    fun getApplication(packageName: String): Application?
    fun insertApplication(application: Application)
    fun updateApplication(application: Application)

    fun reset()
}