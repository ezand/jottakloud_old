package no.ezand.jottakloud

import no.ezand.jottakloud.LogbackLogging.applyLogLevel
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

        // TODO assert props

        val logLevel = environment.getProperty("loglevel", "info")
        applyLogLevel(logLevel)

        val username = environment.getProperty("jottacloud.username")
        val password = environment.getProperty("jottacloud.password")
        val protocol = environment.getProperty("jottacloud.protocol", "https")
        val host = environment.getProperty("jottacloud.host", "www.jottacloud.com")
        val apiPath = environment.getProperty("jottacloud.apipath", "jfs")
        val port = environment.getProperty("jottacloud.port", Int::class.java, 443)
        val baseUrl = URL("$protocol://$host:$port${if (apiPath.isNullOrEmpty()) "" else "/$apiPath"}")
        val authorization = JottacloudAuthorization(username, password)

        val jottacloud = Jottacloud(baseUrl, authorization)

        val user = jottacloud.getUser()
        println("User: $user")

        val device = jottacloud.getDevice((user.devices!!.find { d -> d.name == "Jotta" })!!.name)
        println("Device: $device")

        val mountPoint = jottacloud.getMountPoint(device.name, device.mountPoints!!.find { m -> m.name == "Photos" }!!.name)
        println("MountPoint: $mountPoint")

        val folder = jottacloud.getFolder(device.name, device.mountPoints!!.find { m -> m.name == "Photos" }!!.name, "2017/01")
        println("Folder: $folder")

        val file = jottacloud.getFile(device.name, device.mountPoints!!.find { m -> m.name == "Photos" }!!.name, "2017/01/22/1481270269066.jpg")
        println("File: $file")

        val download = jottacloud.downloadFile(device.name, device.mountPoints!!.find { m -> m.name == "Photos" }!!.name, "2017/01/22/1481270269066.jpg")
        println("Download: ${download.readBytes(file.currentRevision.size.toInt()).size}")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(JottakloudApp::class.java, *args)
}
