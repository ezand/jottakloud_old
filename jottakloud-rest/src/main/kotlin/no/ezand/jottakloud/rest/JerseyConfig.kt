package no.ezand.jottakloud.rest

import no.ezand.jottakloud.rest.endpoints.UserEndpoint
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
open class JerseyConfig : ResourceConfig() {
    init {
        registerClasses(
                UserEndpoint::class.java
        )
    }
}
