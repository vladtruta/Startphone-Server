package com.vladtruta

import com.vladtruta.di.appModule
import com.vladtruta.model.requests.ApplicationListRequest
import com.vladtruta.model.requests.MissingTutorialRequest
import com.vladtruta.model.requests.UserRequest
import com.vladtruta.model.requests.WatchedTutorialRequest
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.koin.core.context.startKoin

private const val KEY_SUCCESS = "success"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(appModule)
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/tutorials/{packageName}") {

        }

        post("/application") {
            val applicationListRequest = call.receive<ApplicationListRequest>()
        }

        post("/user") {
            val userRequest = call.receive<UserRequest>()
        }

        post("/missing") {
            val missingTutorialRequest = call.receive<MissingTutorialRequest>()
        }

        post("/watched") {
            val watchedTutorialRequest = call.receive<WatchedTutorialRequest>()
        }
    }
}

