package no.ezand.jottakloud.cli

import no.ezand.jottakloud.Jottacloud
import no.ezand.jottakloud.JottacloudAuthentication
import no.ezand.jottakloud.LogbackLogging.applyLogLevel
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import java.net.URL

// TODO suppress logging of app started
@SpringBootApplication
open class JottakloudCli {
    @Bean
    open fun init(ctx: ApplicationContext) = CommandLineRunner {
        val environment = ctx.environment

        val logLevel = environment.getProperty("loglevel", "info")
        applyLogLevel(logLevel)

        assertParameters(environment)

        val username = environment.getProperty("jottacloud.username")
        val password = environment.getProperty("jottacloud.password")
        val action = environment.getProperty("action")
        val protocol = environment.getProperty("jottacloud.protocol", "https")
        val host = environment.getProperty("jottacloud.host", "www.jottacloud.com")
        val apiPath = environment.getProperty("jottacloud.apipath", "jfs")
        val port = environment.getProperty("jottacloud.port", Int::class.java, 443)
        val baseUrl = URL("$protocol://$host:$port${if (apiPath.isNullOrEmpty()) "" else "/$apiPath"}")
        val authentication = JottacloudAuthentication(username, password)

        val jottacloud = Jottacloud(baseUrl)

        when (action) {
            "user" -> println("User: ${jottacloud.getUser(authentication)}")
            "device" -> {
                val device = environment.getProperty("device")
                println("Device: ${jottacloud.getDevice(authentication, device)}")
            }
            "mountPoint" -> {
                val device = environment.getProperty("device")
                val mountPoint = environment.getProperty("mountPoint")
                println("MountPoint: ${jottacloud.getMountPoint(authentication, device, mountPoint)}")
            }
            "folder" -> {
                val device = environment.getProperty("device")
                val mountPoint = environment.getProperty("mountPoint")
                val path = environment.getProperty("path")
                println("Folder: ${jottacloud.getFolder(authentication, device, mountPoint, path)}")
            }
            "file" -> {
                val device = environment.getProperty("device")
                val mountPoint = environment.getProperty("mountPoint")
                val path = environment.getProperty("path")
                println("File: ${jottacloud.getFile(authentication, device, mountPoint, path)}")
            }
            "files" -> {
                val device = environment.getProperty("device")
                val mountPoint = environment.getProperty("mountPoint")
                val path = environment.getProperty("path")
                val recursive = environment.getProperty("recursive", "false").toBoolean()
                println("Files: ${jottacloud.getFiles(authentication, device, mountPoint, path, recursive)}")
            }
        }
    }

    fun assertParameters(environment: Environment) {
        val missingParams = listOf("jottacloud.username", "jottacloud.password", "action")
                .map { it to environment.containsProperty(it) }
                .toMap()
                .filterValues { it.not() }

        if (missingParams.isNotEmpty()) {
            println("Missing parameter(s): ${missingParams.keys}")
            System.exit(-1)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(JottakloudCli::class.java, *args)
}
