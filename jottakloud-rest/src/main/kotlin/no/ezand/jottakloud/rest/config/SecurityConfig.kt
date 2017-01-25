package no.ezand.jottakloud.rest.config

import no.ezand.jottakloud.rest.PassthroughAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Component

@EnableWebSecurity
@Component
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    private val authenticationProvider: PassthroughAuthenticationProvider? = PassthroughAuthenticationProvider()

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and().httpBasic()
                .and().logout()
                .and().csrf().disable()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider).eraseCredentials(false)
    }
}