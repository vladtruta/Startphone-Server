package com.vladtruta.repository

import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest

interface IAppRepo {
    fun insertOrUpdateUser(userRequest: UserRequest)
    fun insertOrUpdateApplications(applicationListRequest: ApplicationListRequest)
    fun updateWatchedTutorial(watchedTutorialRequest: WatchedTutorialRequest)
    fun updateTutorialMissing(missingTutorialRequest: MissingTutorialRequest)
}