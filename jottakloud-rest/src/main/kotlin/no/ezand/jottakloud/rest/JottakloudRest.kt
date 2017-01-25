package no.ezand.jottakloud.rest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer

@SpringBootApplication
open class JottakloudRest : SpringBootServletInitializer()

fun main(args: Array<String>) {
    SpringApplication.run(JottakloudRest::class.java, *args)
}
