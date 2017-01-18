package no.ezand.jottakloud

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import khttp.get
import khttp.structures.authorization.BasicAuthorization
import no.ezand.jottakloud.data.User
import org.slf4j.LoggerFactory
import java.net.URL

class JottacloudService(baseUrl: URL, authorization: JottacloudAuthorization) {
    private val logger = LoggerFactory.getLogger(JottacloudService::class.java)
    private val xmlMapper = XmlMapper()
            .registerModule(KotlinModule())
            .registerModule(JacksonXmlModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE)

    private val urlUser = "$baseUrl/${authorization.username}"
    private val basicAuthorization = BasicAuthorization(authorization.username, authorization.password)

    fun getUser(): User {
        logger.debug("Getting user: $urlUser")

        val xml = get(urlUser, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, User::class.java)
    }
}