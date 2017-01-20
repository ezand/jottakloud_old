package no.ezand.jottakloud

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import khttp.get
import khttp.structures.authorization.BasicAuthorization
import no.ezand.jottakloud.data.Device
import no.ezand.jottakloud.data.MountPoint
import no.ezand.jottakloud.data.User
import no.ezand.jottakloud.deserializers.JottacloudDateTimeDeserializer
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.net.URL

// TODO handle notfound etc. and error handling in general
class JottacloudService(baseUrl: URL, authorization: JottacloudAuthorization) {
    private val logger = LoggerFactory.getLogger(JottacloudService::class.java)
    private val xmlMapper = XmlMapper()
            .registerModule(KotlinModule())
            .registerModule(JacksonXmlModule())
            .registerModule(JodaModule().addDeserializer(DateTime::class.java, JottacloudDateTimeDeserializer()))
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE)

    private val urlUser = "$baseUrl/${authorization.username}"

    private val basicAuthorization = BasicAuthorization(authorization.username, authorization.password)

    fun getUser(): User {
        logger.debug("Getting user: $urlUser")

        val xml = get(urlUser, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, User::class.java)
    }

    fun getDevice(deviceName: String): Device {
        val url = "$urlUser/$deviceName"
        logger.debug("Getting device '$deviceName': $url")

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, Device::class.java)
    }

    fun getMountPoint(deviceName: String, mountPointName: String): MountPoint {
        val url = "$urlUser/$deviceName/$mountPointName"
        logger.debug("Getting mount point: '$deviceName' -> '$mountPointName': $url")

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, MountPoint::class.java)
    }
}