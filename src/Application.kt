package com.vladtruta

import com.vladtruta.di.appModule
import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest
import com.vladtruta.repository.IAppRepo
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.core.context.startKoin
import org.koin.ktor.ext.inject

private const val KEY_SUCCESS = "success"
private const val KEY_DATA = "data"
private const val KEY_ERROR = "error"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(appModule)
    }

    val repository by inject<IAppRepo>()

    repository.initialize()

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/tutorials") {
            val packageName = call.request.queryParameters["packageName"]
            if (packageName.isNullOrBlank()) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to "Invalid package name"))
            } else {
                call.respond(mapOf(KEY_SUCCESS to true, KEY_DATA to repository.getTutorialsByPackageName(packageName)))
            }
        }

        post("/application") {
            val applicationListRequest = call.receive<ApplicationListRequest>()
            try {
                repository.insertOrUpdateApplications(applicationListRequest)
                call.respond(mapOf(KEY_SUCCESS to true))
            } catch (e: Exception) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
            }
        }

        post("/user") {
            val userRequest = call.receive<UserRequest>()
            try {
                repository.insertOrUpdateUser(userRequest)
                call.respond(mapOf(KEY_SUCCESS to true))
            } catch (e: Exception) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
            }
        }

        post("/missing") {
            val missingTutorialRequest = call.receive<MissingTutorialRequest>()
            try {
                repository.updateTutorialMissing(missingTutorialRequest)
                call.respond(mapOf(KEY_SUCCESS to true))
            } catch (e: Exception) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
            }
        }

        post("/watched") {
            val watchedTutorialRequest = call.receive<WatchedTutorialRequest>()
            try {
                repository.updateWatchedTutorial(watchedTutorialRequest)
                call.respond(mapOf(KEY_SUCCESS to true))
            } catch (e: Exception) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
            }
        }
    }
}

