package no.ezand.jottakloud.rest.endpoints

import no.ezand.jottakloud.Jottacloud
import no.ezand.jottakloud.JottacloudAuthorization
import no.ezand.jottakloud.data.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.net.URL
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

// TODO
@Component
@Path("/user")
@Produces("application/json")
open class UserEndpoint {

    @GET
    fun user(): User {
        return jottacloud().getUser()!!
    }

    private fun jottacloud(): Jottacloud {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        val username = token.name
        val password = token.credentials as String
        return Jottacloud(URL("https://www.jottacloud.com/jfs"), JottacloudAuthorization(username, password))
    }
}
