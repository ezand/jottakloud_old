package no.ezand.jottakloud.rest

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
open class PassthroughAuthenticationProvider : AuthenticationProvider{
    override fun authenticate(authentication: Authentication?): Authentication {
        return authentication!!
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
