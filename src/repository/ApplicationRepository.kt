package com.vladtruta.repository

import com.vladtruta.model.local.UsersTutorialMissing
import com.vladtruta.model.local.UsersTutorialsWatched
import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest
import com.vladtruta.persistence.IAppDao

class ApplicationRepository(private val applicationDao: IAppDao) : IAppRepo {

    override fun insertOrUpdateUser(userRequest: UserRequest) {
        val user = userRequest.toUser() ?: throw Exception("Invalid user request")

        val userExists = applicationDao.getUserByIdToken(user.idToken)
        userExists?.let {
            applicationDao.updateUser(user)
        } ?: run {
            applicationDao.insertUser(user)
        }
    }

    override fun insertOrUpdateApplications(applicationListRequest: ApplicationListRequest) {
        val applications = applicationListRequest.applications?.map {
            it.toApplication() ?: throw Exception("Invalid application: $it")
        } ?: throw Exception("Invalid application list")

        applications.forEach { application ->
            val applicationExists = applicationDao.getApplication(application.packageName)

            applicationExists?.let {
                applicationDao.updateApplication(application)
            } ?: run {
                applicationDao.insertApplication(application)
            }
        }
    }

    override fun updateWatchedTutorial(watchedTutorialRequest: WatchedTutorialRequest) {
        val watchedTutorial =
            watchedTutorialRequest.toWatchedTutorial() ?: throw Exception("Invalid watched tutorial request")

        val watchedTutorialExists =
            applicationDao.getWatchedTutorial(watchedTutorial.idToken, watchedTutorial.tutorialId)
        watchedTutorialExists?.let {
            val givenRating = if (watchedTutorial.useful) 1.0 else 0.0
            val newRating = (it.rating + givenRating) / 2

            applicationDao.updateWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.idToken,
                    watchedTutorial.tutorialId,
                    it.watchCount + 1,
                    newRating
                )
            )
        } ?: run {
            applicationDao.insertWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.idToken,
                    watchedTutorial.tutorialId,
                    1,
                    if (watchedTutorial.useful) 1.0 else 0.0
                )
            )
        }
    }

    override fun updateTutorialMissing(missingTutorialRequest: MissingTutorialRequest) {
        val tutorialMissing =
            missingTutorialRequest.toMissingTutorial() ?: throw Exception("Invalid missing tutorial request")

        val tutorialMissingExists =
            applicationDao.getTutorialMissing(tutorialMissing.idToken, tutorialMissing.packageName)
        tutorialMissingExists?.let {
            applicationDao.updateTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.idToken,
                    tutorialMissing.packageName,
                    it.requestCount + 1
                )
            )
        } ?: run {
            applicationDao.insertTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.idToken,
                    tutorialMissing.packageName,
                    1
                )
            )
        }
    }
}