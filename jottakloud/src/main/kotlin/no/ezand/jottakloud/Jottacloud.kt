package no.ezand.jottakloud

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import khttp.get
import khttp.structures.authorization.BasicAuthorization
import mu.KLogging
import no.ezand.jottakloud.data.*
import no.ezand.jottakloud.deserializers.JottacloudDateTimeDeserializer
import org.joda.time.DateTime
import java.io.InputStream
import java.net.URL
import java.net.URLEncoder

// TODO handle notfound etc. and error handling in general
class Jottacloud(val baseUrl: URL, authorization: JottacloudAuthorization) {
    companion object : KLogging()

    private val xmlMapper = XmlMapper()
            .registerModule(KotlinModule())
            .registerModule(JacksonXmlModule())
            .registerModule(JodaModule().addDeserializer(DateTime::class.java, JottacloudDateTimeDeserializer()))
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE)

    private val user = authorization.username
    private val basicAuthorization = BasicAuthorization(authorization.username, authorization.password)

    fun getUser(): User {
        val url = buildUrl(user)
        logger.debug { "Getting user: $url" }

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, User::class.java)
    }

    fun getDevice(deviceName: String): Device {
        val url = buildUrl("$user/$deviceName")
        logger.debug { "Getting device '$deviceName': $url" }

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, Device::class.java)
    }

    fun getMountPoint(deviceName: String, mountPointName: String): MountPoint {
        val url = buildUrl("$user/$deviceName/$mountPointName")
        logger.debug { "Getting mount point: '$deviceName' -> '$mountPointName': $url" }

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, MountPoint::class.java)
    }

    fun getFolder(deviceName: String, mountPointName: String, path: String): FolderDetails {
        val url = buildUrl("$user/$deviceName/$mountPointName/$path")
        logger.debug { "Getting folder: '$deviceName' -> '$mountPointName' -> '$path': $url" }

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, FolderDetails::class.java)
    }

    fun getFile(deviceName: String, mountPointName: String, path: String): FileDetails {
        val url = buildUrl("$user/$deviceName/$mountPointName/$path")
        logger.debug { "Getting file: '$deviceName' -> '$mountPointName' -> '$path': $url" }

        val xml = get(url, auth = basicAuthorization).text
        return xmlMapper.readValue(xml, FileDetails::class.java)
    }

    fun downloadFile(deviceName: String, mountPointName: String, path: String): InputStream {
        val url = buildUrl("$user/$deviceName/$mountPointName/$path?mode=bin")
        logger.debug { "Getting file: '$deviceName' -> '$mountPointName' -> '$path': $url" }

        return get(url, auth = basicAuthorization, stream = true).raw
    }

    private fun buildUrl(url: String): String {
        val encodedUrl = url.split("/").map { URLEncoder.encode(it, "utf-8") }.joinToString("/")

        return "$baseUrl/$encodedUrl"
    }
}
