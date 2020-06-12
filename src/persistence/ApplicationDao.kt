package com.vladtruta.persistence

import com.vladtruta.model.local.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ApplicationDao(private val appDatabase: Database) : IAppDao {

    override fun getTutorials(): List<Tutorial> = transaction(appDatabase) {
        TutorialEntity.selectAll().map {
            Tutorial(
                it[TutorialEntity.id],
                it[TutorialEntity.packageName],
                it[TutorialEntity.title],
                it[TutorialEntity.videoUrl]
            )
        }
    }

    override fun insertTutorial(tutorial: Tutorial) = transaction(appDatabase) {
        TutorialEntity.insert { insertStatement ->
            insertStatement[id] = tutorial.id
            insertStatement[packageName] = tutorial.packageName
            insertStatement[title] = tutorial.title
            insertStatement[videoUrl] = tutorial.videoUrl
        }
        Unit
    }

    override fun updateTutorial(tutorial: Tutorial) = transaction(appDatabase) {
        TutorialEntity.update({ TutorialEntity.id eq tutorial.id }) { updateStatement ->
            updateStatement[id] = tutorial.id
            updateStatement[packageName] = tutorial.packageName
            updateStatement[title] = tutorial.title
            updateStatement[videoUrl] = tutorial.videoUrl
        }
        Unit
    }

    override fun deleteTutorial(id: Int) = transaction(appDatabase) {
        TutorialEntity.deleteWhere { TutorialEntity.id eq id }
        Unit
    }

    override fun getUserByIdToken(idToken: String): User? = transaction(appDatabase) {
        UserEntity.select { UserEntity.idToken eq idToken }.map {
            User(
                it[UserEntity.idToken],
                it[UserEntity.dateOfBirth],
                it[UserEntity.gender]
            )
        }.firstOrNull()
    }

    override fun insertUser(user: User) = transaction(appDatabase) {
        UserEntity.insert { insertStatement ->
            insertStatement[idToken] = user.idToken
            insertStatement[gender] = user.gender
            insertStatement[dateOfBirth] = user.dateOfBirth
        }
        Unit
    }

    override fun updateUser(user: User) = transaction(appDatabase) {
        UserEntity.update({ UserEntity.idToken eq user.idToken }) { updateStatement ->
            updateStatement[idToken] = user.idToken
            updateStatement[gender] = user.gender
            updateStatement[dateOfBirth] = user.dateOfBirth
        }
        Unit
    }

    override fun getWatchedTutorial(idToken: String, tutorialId: Int): UsersTutorialsWatched? =
        transaction(appDatabase) {
            UsersTutorialsWatchedEntity.select {
                (UsersTutorialsWatchedEntity.userIdToken eq idToken) and (UsersTutorialsWatchedEntity.tutorialId eq tutorialId)
            }.map {
                UsersTutorialsWatched(
                    it[UsersTutorialsWatchedEntity.userIdToken],
                    it[UsersTutorialsWatchedEntity.tutorialId],
                    it[UsersTutorialsWatchedEntity.watchCount],
                    it[UsersTutorialsWatchedEntity.rating]
                )
            }.firstOrNull()
        }

    override fun insertWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched) = transaction(appDatabase) {
        UsersTutorialsWatchedEntity.insert { insertStatement ->
            insertStatement[userIdToken] = usersTutorialsWatched.userIdToken
            insertStatement[tutorialId] = usersTutorialsWatched.tutorialId
            insertStatement[watchCount] = usersTutorialsWatched.watchCount
            insertStatement[rating] = usersTutorialsWatched.rating
        }
        Unit
    }

    override fun updateWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched) = transaction(appDatabase) {
        UsersTutorialsWatchedEntity.update({ (UsersTutorialsWatchedEntity.userIdToken eq usersTutorialsWatched.userIdToken) and (UsersTutorialsWatchedEntity.tutorialId eq usersTutorialsWatched.tutorialId) }) { updateStatement ->
            updateStatement[userIdToken] = usersTutorialsWatched.userIdToken
            updateStatement[tutorialId] = usersTutorialsWatched.tutorialId
            updateStatement[watchCount] = usersTutorialsWatched.watchCount
            updateStatement[rating] = usersTutorialsWatched.rating
        }
        Unit
    }

    override fun getTutorialMissing(idToken: String, packageName: String): UsersTutorialMissing? =
        transaction(appDatabase) {
            UsersTutorialMissingRequestsEntity.select {
                (UsersTutorialMissingRequestsEntity.userIdToken eq idToken) and (UsersTutorialMissingRequestsEntity.packageName eq packageName)
            }.map {
                UsersTutorialMissing(
                    it[UsersTutorialMissingRequestsEntity.userIdToken],
                    it[UsersTutorialMissingRequestsEntity.packageName],
                    it[UsersTutorialMissingRequestsEntity.requestCount]
                )
            }.firstOrNull()
        }

    override fun insertTutorialMissing(usersTutorialMissing: UsersTutorialMissing) = transaction(appDatabase) {
        UsersTutorialMissingRequestsEntity.insert { insertStatement ->
            insertStatement[userIdToken] = usersTutorialMissing.userIdToken
            insertStatement[packageName] = usersTutorialMissing.packageName
            insertStatement[requestCount] = usersTutorialMissing.requestCount
        }
        Unit
    }

    override fun updateTutorialMissing(usersTutorialMissing: UsersTutorialMissing) = transaction(appDatabase) {
        UsersTutorialMissingRequestsEntity.update({ (UsersTutorialMissingRequestsEntity.userIdToken eq usersTutorialMissing.userIdToken) and (UsersTutorialMissingRequestsEntity.packageName eq usersTutorialMissing.packageName) }) { updateStatement ->
            updateStatement[userIdToken] = usersTutorialMissing.userIdToken
            updateStatement[packageName] = usersTutorialMissing.packageName
            updateStatement[requestCount] = usersTutorialMissing.requestCount
        }
        Unit
    }

    override fun getApplication(packageName: String): Application? = transaction(appDatabase) {
        ApplicationEntity.select { ApplicationEntity.packageName eq packageName }
            .map {
                Application(
                    it[ApplicationEntity.packageName],
                    it[ApplicationEntity.name]
                )
            }.firstOrNull()
    }

    override fun insertApplication(application: Application) = transaction(appDatabase) {
        ApplicationEntity.insert { insertStatement ->
            insertStatement[packageName] = application.packageName
            insertStatement[name] = application.name
        }
        Unit
    }

    override fun updateApplication(application: Application) {
        ApplicationEntity.update({ ApplicationEntity.packageName eq application.packageName }) { updateStatement ->
            updateStatement[packageName] = application.packageName
            updateStatement[name] = application.name
        }
        Unit
    }
}