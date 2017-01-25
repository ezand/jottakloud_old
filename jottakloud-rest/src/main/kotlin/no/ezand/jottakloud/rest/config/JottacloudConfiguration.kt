package no.ezand.jottakloud.rest.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URL

@ConfigurationProperties(prefix = "jottacloud")
class JottacloudConfiguration {
    var protocol: String = "https"
    var host: String = "www.jottacloud.com"
    val port: Int = 443
    val apiPath: String = "/jfs"

    fun basePath() = URL(protocol, host, port, apiPath)
}
