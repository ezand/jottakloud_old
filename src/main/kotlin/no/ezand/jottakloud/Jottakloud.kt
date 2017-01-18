package no.ezand.jottakloud

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import khttp.get
import khttp.structures.authorization.BasicAuthorization

@SpringBootApplication
open class Jottakloud {
    @Bean
    open fun init(ctx: ApplicationContext) = CommandLineRunner {
        val environment = ctx.environment

        val jottacloudUsername = environment.getProperty("jottacloud.username")
        val jottacloudPassword = environment.getProperty("jottacloud.password")

        println("Username: $jottacloudUsername")
        println("Password: $jottacloudPassword")

        println(
                get("https://www.jottacloud.com/jfs/$jottacloudUsername", auth = BasicAuthorization(jottacloudUsername, jottacloudPassword)).text
        )
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Jottakloud::class.java, *args)
}
