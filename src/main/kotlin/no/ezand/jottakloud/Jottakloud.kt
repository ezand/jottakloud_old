package no.ezand.jottakloud

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Jottakloud {
}

fun main(args: Array<String>) {
    SpringApplication.run(Jottakloud::class.java, *args)
}
