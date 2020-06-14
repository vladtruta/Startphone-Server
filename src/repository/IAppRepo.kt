package com.vladtruta.repository

import com.vladtruta.model.requests.*
import com.vladtruta.model.responses.TutorialResponse

interface IAppRepo {
    fun initialize()

    fun getTutorialsByPackageName(packageName: String): List<TutorialResponse>
    fun insertOrUpdateUser(userRequest: UserRequest)
    fun insertOrUpdateApplications(applicationListRequest: ApplicationListRequest)
    fun insertOrUpdateWatchedTutorial(id: String, watchedTutorialRequest: WatchedTutorialRequest)
    fun insertOrUpdateRatedTutorial(id: String, ratedTutorialRequest: RatedTutorialRequest)
    fun insertOrUpdateTutorialMissing(id: String, missingTutorialRequest: MissingTutorialRequest)
}