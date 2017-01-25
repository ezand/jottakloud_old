package no.ezand.jottakloud.rest.endpoints

import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path

@Component
@Path("/user")
open class UserEndpoint {
    @GET
    fun user(): String {
        return "Hello World!!!!"
    }
}
