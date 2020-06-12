package com.vladtruta.repository

import com.vladtruta.model.local.Tutorial
import com.vladtruta.model.local.UsersTutorialMissing
import com.vladtruta.model.local.UsersTutorialsWatched
import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest
import com.vladtruta.model.responses.TutorialResponse
import com.vladtruta.persistence.IAppDao

class ApplicationRepository(private val applicationDao: IAppDao) : IAppRepo {

    override fun initialize() {
        applicationDao.reset()

        applications.forEach { applicationDao.insertApplication(it) }
        tutorials.forEach { applicationDao.insertTutorial(it) }
    }

    override fun getTutorialsByPackageName(packageName: String): List<TutorialResponse> {
        return applicationDao.getTutorials().filter { it.packageName == packageName }.map { it.toTutorialResponse() }
    }

    override fun insertOrUpdateUser(userRequest: UserRequest) {
        val user = userRequest.toUser() ?: throw Exception("Invalid user request")

        val userExists = applicationDao.getUser(user.email)
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

    override fun updateWatchedTutorial(email: String, watchedTutorialRequest: WatchedTutorialRequest) {
        val watchedTutorial =
            watchedTutorialRequest.toWatchedTutorial(email) ?: throw Exception("Invalid watched tutorial request")

        val watchedTutorialExists =
            applicationDao.getWatchedTutorial(watchedTutorial.email, watchedTutorial.tutorialId)
        watchedTutorialExists?.let {
            val givenRating = if (watchedTutorial.useful) 1.0 else 0.0

            // Compute rating for tutorial, according to each user
            val newRating = (it.rating + givenRating) / 2

            val tutorial = applicationDao.getTutorial(watchedTutorial.tutorialId)
            tutorial?.let { existingTutorial ->
                // Compute overall rating for tutorial
                val newOverallRating = (existingTutorial.rating + givenRating) / 2

                applicationDao.updateTutorial(
                    Tutorial(
                        existingTutorial.packageName,
                        existingTutorial.title,
                        existingTutorial.videoUrl,
                        newOverallRating,
                        existingTutorial.id
                    )
                )
            }

            applicationDao.updateWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.email,
                    watchedTutorial.tutorialId,
                    it.watchCount + 1,
                    newRating
                )
            )
        } ?: run {
            applicationDao.insertWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.email,
                    watchedTutorial.tutorialId,
                    1,
                    if (watchedTutorial.useful) 1.0 else 0.0
                )
            )
        }
    }

    override fun updateTutorialMissing(email: String, missingTutorialRequest: MissingTutorialRequest) {
        val tutorialMissing =
            missingTutorialRequest.toMissingTutorial(email) ?: throw Exception("Invalid missing tutorial request")

        val tutorialMissingExists =
            applicationDao.getTutorialMissing(tutorialMissing.email, tutorialMissing.packageName)
        tutorialMissingExists?.let {
            applicationDao.updateTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.email,
                    tutorialMissing.packageName,
                    it.requestCount + 1
                )
            )
        } ?: run {
            applicationDao.insertTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.email,
                    tutorialMissing.packageName,
                    1
                )
            )
        }
    }
}