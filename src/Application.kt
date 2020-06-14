package com.vladtruta

import com.vladtruta.di.appModule
import com.vladtruta.di.databaseModule
import com.vladtruta.model.jwt.SimpleJWT
import com.vladtruta.model.requests.*
import com.vladtruta.repository.IAppRepo
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.auth.principal
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
        modules(appModule + databaseModule)
    }

    val repository by inject<IAppRepo>()

    repository.initialize()

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val simpleJwt = SimpleJWT("startphone-jwt-secret")
    install(Authentication) {
        jwt {
            verifier(simpleJwt.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("id").asString())
            }
        }
    }

    routing {
        post("/user") {
            val userRequest = call.receive<UserRequest>()
            try {
                repository.insertOrUpdateUser(userRequest)

                val authToken = simpleJwt.sign(userRequest.id!!)
                call.respond(mapOf(KEY_SUCCESS to true, KEY_DATA to authToken))
            } catch (e: Exception) {
                call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
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

       // authenticate {
            get("/tutorials") {
                val packageName = call.request.queryParameters["packageName"]
                if (packageName.isNullOrBlank()) {
                    call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to "Invalid package name"))
                } else {
                    call.respond(
                        mapOf(
                            KEY_SUCCESS to true,
                            KEY_DATA to repository.getTutorialsByPackageName(packageName)
                        )
                    )
                }
            }

            post("/missing") {
                val missingTutorialRequest = call.receive<MissingTutorialRequest>()
                try {
                    val id = call.principal<UserIdPrincipal>()?.name ?: error("Invalid Session")
                    repository.insertOrUpdateTutorialMissing(id, missingTutorialRequest)
                    call.respond(mapOf(KEY_SUCCESS to true))
                } catch (e: Exception) {
                    call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
                }
            }

            post("/watched") {
                val watchedTutorialRequest = call.receive<WatchedTutorialRequest>()
                try {
                    val id = call.principal<UserIdPrincipal>()?.name ?: error("Invalid Session")
                    repository.insertOrUpdateWatchedTutorial(id, watchedTutorialRequest)
                    call.respond(mapOf(KEY_SUCCESS to true))
                } catch (e: Exception) {
                    call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
                }
            }

            post("/rated") {
                val ratedTutorialRequest = call.receive<RatedTutorialRequest>()
                try {
                    val id = call.principal<UserIdPrincipal>()?.name ?: error("Invalid Session")
                    repository.insertOrUpdateRatedTutorial(id, ratedTutorialRequest)
                    call.respond(mapOf(KEY_SUCCESS to true))
                } catch (e: Exception) {
                    call.respond(mapOf(KEY_SUCCESS to false, KEY_ERROR to e.message))
                }
            }
       // }
    }
}

