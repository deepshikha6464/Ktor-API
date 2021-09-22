package com.deeps.routes

import com.deeps.API_VERSION
import com.deeps.Repository.userRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.postgresql.jdbc.EscapedFunctions.USER

const val CREATE_USER = "$API_VERSION/user"

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(USER)
class User
fun Route.user(
    db:userRepository
) {
    post<User> {
       val parameter = call.receive<Parameters>()

        val userID = parameter["userId"] ?: return@post call.respondText(
            "missing field",
            status = HttpStatusCode.Unauthorized
        )
        val name = parameter["name"] ?: return@post call.respondText(
            "missing field",
            status = HttpStatusCode.Unauthorized
        )

        val age = parameter["age"] ?: return@post call.respondText(
            "missing field",
            status = HttpStatusCode.Unauthorized
        )

        try {
            val newUser = db.insert(userID.toInt() , name,age.toInt())
           newUser?.userId?.let {
               call.respond(  status = HttpStatusCode.Created,newUser)
           }
        }catch (e:Throwable){
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating User")
        }

    }
}

