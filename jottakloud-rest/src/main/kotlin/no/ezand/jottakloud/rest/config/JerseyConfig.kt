package no.ezand.jottakloud.rest.config

import no.ezand.jottakloud.Jottacloud
import no.ezand.jottakloud.rest.endpoints.UserEndpoint
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JottacloudConfiguration::class)
open class JerseyConfig(@Autowired private val jottacloudConfiguration: JottacloudConfiguration) : ResourceConfig() {

    @Bean
    open fun jottacloud() = Jottacloud(jottacloudConfiguration.basePath())

    init {
        registerClasses(
                UserEndpoint::class.java
        )
    }
}
