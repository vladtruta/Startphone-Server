package com.vladtruta.repository

import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest
import com.vladtruta.model.responses.TutorialResponse

interface IAppRepo {
    fun getTutorialsByPackageName(packageName: String): List<TutorialResponse>
    fun insertOrUpdateUser(userRequest: UserRequest)
    fun insertOrUpdateApplications(applicationListRequest: ApplicationListRequest)
    fun updateWatchedTutorial(watchedTutorialRequest: WatchedTutorialRequest)
    fun updateTutorialMissing(missingTutorialRequest: MissingTutorialRequest)
}