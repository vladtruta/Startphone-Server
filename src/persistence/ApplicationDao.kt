package com.vladtruta.persistence

import com.vladtruta.model.local.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ApplicationDao(private val appDatabase: Database) : IAppDao {

    override fun getTutorial(id: Int): Tutorial? = transaction(appDatabase) {
        TutorialEntity.select { TutorialEntity.id eq id }.map {
            Tutorial(
                    it[TutorialEntity.packageName],
                    it[TutorialEntity.title],
                    it[TutorialEntity.videoUrl],
                    it[TutorialEntity.rating],
                    it[TutorialEntity.id]
            )
        }.firstOrNull()
    }

    override fun getTutorials(): List<Tutorial> = transaction(appDatabase) {
        TutorialEntity.selectAll().map {
            Tutorial(
                    it[TutorialEntity.packageName],
                    it[TutorialEntity.title],
                    it[TutorialEntity.videoUrl],
                    it[TutorialEntity.rating],
                    it[TutorialEntity.id]
            )
        }
    }

    override fun insertTutorial(tutorial: Tutorial) = transaction(appDatabase) {
        TutorialEntity.insert { insertStatement ->
            insertStatement[packageName] = tutorial.packageName
            insertStatement[title] = tutorial.title
            insertStatement[videoUrl] = tutorial.videoUrl
            insertStatement[rating] = tutorial.rating
        }
        Unit
    }

    override fun updateTutorial(tutorial: Tutorial) = transaction(appDatabase) {
        TutorialEntity.update({ TutorialEntity.id eq tutorial.id }) { updateStatement ->
            updateStatement[id] = tutorial.id
            updateStatement[packageName] = tutorial.packageName
            updateStatement[title] = tutorial.title
            updateStatement[videoUrl] = tutorial.videoUrl
            updateStatement[rating] = tutorial.rating
        }
        Unit
    }

    override fun deleteTutorial(id: Int) = transaction(appDatabase) {
        TutorialEntity.deleteWhere { TutorialEntity.id eq id }
        Unit
    }

    override fun getUser(id: String): User? = transaction(appDatabase) {
        UserEntity.select { UserEntity.id eq id }.map {
            User(
                    it[UserEntity.id],
                    it[UserEntity.dateOfBirth].toLocalDate(),
                    it[UserEntity.gender],
                    it[UserEntity.email]
            )
        }.firstOrNull()
    }

    override fun insertUser(user: User) = transaction(appDatabase) {
        UserEntity.insert { insertStatement ->
            insertStatement[id] = user.id
            insertStatement[gender] = user.gender
            insertStatement[dateOfBirth] = user.dateOfBirth.toDateTimeAtStartOfDay()
            insertStatement[email] = user.email
        }
        Unit
    }

    override fun updateUser(user: User) = transaction(appDatabase) {
        UserEntity.update({ UserEntity.id eq user.id }) { updateStatement ->
            updateStatement[id] = user.id
            updateStatement[gender] = user.gender
            updateStatement[dateOfBirth] = user.dateOfBirth.toDateTimeAtStartOfDay()
            updateStatement[email] = user.email
        }
        Unit
    }

    override fun getWatchedTutorial(id: String, tutorialId: Int): UsersTutorialsWatched? = transaction(appDatabase) {
        UsersTutorialsWatchedEntity.select {
            (UsersTutorialsWatchedEntity.userId eq id) and (UsersTutorialsWatchedEntity.tutorialId eq tutorialId)
        }.map {
            UsersTutorialsWatched(
                    it[UsersTutorialsWatchedEntity.userId],
                    it[UsersTutorialsWatchedEntity.tutorialId],
                    it[UsersTutorialsWatchedEntity.watchCount],
                    it[UsersTutorialsWatchedEntity.rating]
            )
        }.firstOrNull()
    }

    override fun insertWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched) = transaction(appDatabase) {
        UsersTutorialsWatchedEntity.insert { insertStatement ->
            insertStatement[userId] = usersTutorialsWatched.userId
            insertStatement[tutorialId] = usersTutorialsWatched.tutorialId
            insertStatement[watchCount] = usersTutorialsWatched.watchCount
            insertStatement[rating] = usersTutorialsWatched.rating
        }
        Unit
    }

    override fun updateWatchedTutorial(usersTutorialsWatched: UsersTutorialsWatched) = transaction(appDatabase) {
        UsersTutorialsWatchedEntity.update({ (UsersTutorialsWatchedEntity.userId eq usersTutorialsWatched.userId) and (UsersTutorialsWatchedEntity.tutorialId eq usersTutorialsWatched.tutorialId) }) { updateStatement ->
            updateStatement[userId] = usersTutorialsWatched.userId
            updateStatement[tutorialId] = usersTutorialsWatched.tutorialId
            updateStatement[watchCount] = usersTutorialsWatched.watchCount
            updateStatement[rating] = usersTutorialsWatched.rating
        }
        Unit
    }

    override fun getTutorialMissing(id: String, packageName: String): UsersTutorialMissing? = transaction(appDatabase) {
        UsersTutorialMissingRequestsEntity.select {
            (UsersTutorialMissingRequestsEntity.userId eq id) and (UsersTutorialMissingRequestsEntity.packageName eq packageName)
        }.map {
            UsersTutorialMissing(
                    it[UsersTutorialMissingRequestsEntity.userId],
                    it[UsersTutorialMissingRequestsEntity.packageName],
                    it[UsersTutorialMissingRequestsEntity.requestCount]
            )
        }.firstOrNull()
    }

    override fun insertTutorialMissing(usersTutorialMissing: UsersTutorialMissing) = transaction(appDatabase) {
        UsersTutorialMissingRequestsEntity.insert { insertStatement ->
            insertStatement[userId] = usersTutorialMissing.userId
            insertStatement[packageName] = usersTutorialMissing.packageName
            insertStatement[requestCount] = usersTutorialMissing.requestCount
        }
        Unit
    }

    override fun updateTutorialMissing(usersTutorialMissing: UsersTutorialMissing) = transaction(appDatabase) {
        UsersTutorialMissingRequestsEntity.update({ (UsersTutorialMissingRequestsEntity.userId eq usersTutorialMissing.userId) and (UsersTutorialMissingRequestsEntity.packageName eq usersTutorialMissing.packageName) }) { updateStatement ->
            updateStatement[userId] = usersTutorialMissing.userId
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

    override fun updateApplication(application: Application) = transaction(appDatabase) {
        ApplicationEntity.update({ ApplicationEntity.packageName eq application.packageName }) { updateStatement ->
            updateStatement[packageName] = application.packageName
            updateStatement[name] = application.name
        }
        Unit
    }

    override fun reset() = transaction(appDatabase) {
        SchemaUtils.drop(UsersTutorialMissingRequestsEntity, UsersTutorialsWatchedEntity, UserEntity, ApplicationEntity, TutorialEntity)
        SchemaUtils.create(UsersTutorialMissingRequestsEntity, UsersTutorialsWatchedEntity, UserEntity, ApplicationEntity, TutorialEntity)
    }
}