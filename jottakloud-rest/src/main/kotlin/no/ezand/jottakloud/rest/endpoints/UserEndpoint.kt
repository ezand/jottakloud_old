package no.ezand.jottakloud.rest.endpoints

import no.ezand.jottakloud.Jottacloud
import no.ezand.jottakloud.JottacloudAuthentication
import no.ezand.jottakloud.data.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

// TODO
@Component
@Path("/user")
@Produces("application/json")
open class UserEndpoint(@Autowired private val jottacloud: Jottacloud) {

    @GET
    fun user(): User {
        return jottacloud.getUser(authentication())!!
    }

    private fun authentication(): JottacloudAuthentication {
        val token = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        val username = token.name
        val password = token.credentials as String
        return JottacloudAuthentication(username, password)
    }
}
