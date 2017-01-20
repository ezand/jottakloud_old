package no.ezand.jottakloud

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.net.URL

@SpringBootApplication
open class JottakloudApp {
    @Bean
    open fun init(ctx: ApplicationContext) = CommandLineRunner {
        val environment = ctx.environment

        val username = environment.getProperty("jottacloud.username")
        val password = environment.getProperty("jottacloud.password")
        val protocol = environment.getProperty("jottacloud.protocol", "https")
        val host = environment.getProperty("jottacloud.host", "www.jottacloud.com")
        val apiPath = environment.getProperty("jottacloud.apipath", "jfs")
        val port = environment.getProperty("jottacloud.port", Int::class.java, 443)
        val baseUrl = URL("$protocol://$host:$port${if (apiPath.isNullOrEmpty()) "" else "/$apiPath" }")
        val authorization = JottacloudAuthorization(username, password)

        val jottacloudService = JottacloudService(baseUrl, authorization)

        val user = jottacloudService.getUser()
        println("User: $user")

        val device = jottacloudService.getDevice((user.devices!!.find { d -> d.name == "Jotta" })!!.name)
        println("Device: $device")

        val mountPoint = jottacloudService.getMountPoint(device.name, device.mountPoints!!.last().name)
        println("MountPoint: $mountPoint")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(JottakloudApp::class.java, *args)
}
