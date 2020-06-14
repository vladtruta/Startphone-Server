package com.vladtruta.repository

import com.vladtruta.model.local.Tutorial
import com.vladtruta.model.local.UsersTutorialMissing
import com.vladtruta.model.local.UsersTutorialsRated
import com.vladtruta.model.local.UsersTutorialsWatched
import com.vladtruta.model.requests.*
import com.vladtruta.model.responses.TutorialResponse
import com.vladtruta.persistence.IAppDao

class ApplicationRepository(private val applicationDao: IAppDao) : IAppRepo {

    override fun initialize() {
        applications.forEach { application ->
            val appExists = applicationDao.getApplication(application.packageName)

            appExists?.let {
                applicationDao.updateApplication(it)
            } ?: run {
                applicationDao.insertApplication(application)
            }
        }

        tutorials.forEach { tutorial ->
            val tutorialExists = applicationDao.getTutorial(tutorial.id)

            tutorialExists?.let {
                applicationDao.updateTutorial(tutorial)
            } ?: run {
                applicationDao.insertTutorial(tutorial)
            }
        }
    }

    override fun getTutorialsByPackageName(packageName: String): List<TutorialResponse> {
        return applicationDao.getTutorials().filter { it.packageName == packageName }.map { it.toTutorialResponse() }
    }

    override fun insertOrUpdateUser(userRequest: UserRequest) {
        val user = userRequest.toUser() ?: throw Exception("Invalid user request")

        val userExists = applicationDao.getUser(user.id)
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

    override fun insertOrUpdateWatchedTutorial(id: String, watchedTutorialRequest: WatchedTutorialRequest) {
        val watchedTutorial =
            watchedTutorialRequest.toWatchedTutorial(id) ?: throw Exception("Invalid watched tutorial request")

        val watchedTutorialExists =
            applicationDao.getWatchedTutorial(watchedTutorial.id, watchedTutorial.tutorialId)
        watchedTutorialExists?.let {
            applicationDao.updateWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.id,
                    watchedTutorial.tutorialId,
                    it.watchCount + 1
                )
            )
        } ?: run {
            applicationDao.insertWatchedTutorial(
                UsersTutorialsWatched(
                    watchedTutorial.id,
                    watchedTutorial.tutorialId,
                    1
                )
            )
        }
    }

    override fun insertOrUpdateRatedTutorial(id: String, ratedTutorialRequest: RatedTutorialRequest) {
        val ratedTutorial =
            ratedTutorialRequest.toRatedTutorial(id) ?: throw Exception("Invalid rated tutorial request")

        val ratedTutorialExists = applicationDao.getRatedTutorial(ratedTutorial.id, ratedTutorial.tutorialId)
        ratedTutorialExists?.let {
            val givenRating = if (ratedTutorial.useful) 1.0 else 0.0

            // Compute rating for tutorial, according to each user
            val newRating = (it.rating + givenRating) / 2

            val tutorial = applicationDao.getTutorial(ratedTutorial.tutorialId)
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

            applicationDao.updateRatedTutorial(
                UsersTutorialsRated(
                    ratedTutorial.id,
                    ratedTutorial.tutorialId,
                    newRating
                )
            )
        } ?: run {
            applicationDao.insertRatedTutorial(
                UsersTutorialsRated(
                    ratedTutorial.id,
                    ratedTutorial.tutorialId,
                    if (ratedTutorial.useful) 1.0 else 0.0
                )
            )
        }
    }

    override fun insertOrUpdateTutorialMissing(id: String, missingTutorialRequest: MissingTutorialRequest) {
        val tutorialMissing =
            missingTutorialRequest.toMissingTutorial(id) ?: throw Exception("Invalid missing tutorial request")

        val tutorialMissingExists =
            applicationDao.getTutorialMissing(tutorialMissing.id, tutorialMissing.packageName)
        tutorialMissingExists?.let {
            applicationDao.updateTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.id,
                    tutorialMissing.packageName,
                    it.requestCount + 1
                )
            )
        } ?: run {
            applicationDao.insertTutorialMissing(
                UsersTutorialMissing(
                    tutorialMissing.id,
                    tutorialMissing.packageName,
                    1
                )
            )
        }
    }
}