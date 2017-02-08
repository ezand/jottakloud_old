package no.ezand.jottakloud.utils

import no.ezand.jottakloud.Jottacloud
import no.ezand.jottakloud.JottacloudAuthentication
import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.net.URL

class Md5Helper(private val jottacloud: Jottacloud) {
    fun remoteMd5s(auth: JottacloudAuthentication, path: String): Map<String, String> {
        val device = ""
        val mountPoint = ""
        val folderPath = ""
        jottacloud.getFolder(auth, device, mountPoint, folderPath)
        return mapOf()
    }

    fun localMd5s(folder: File): Map<String, String> {
        return folder.walk()
                .filter { it.isFile }
                .map { Pair(it.absolutePath.replace(folder.absolutePath, ""), DigestUtils.md5Hex(it.inputStream())) }
                .toMap()
    }
}

/*fun main(args: Array<String>) {
    val file = File("/Users/eirik/test")
    val files = Md5Helper(Jottacloud(URL("http://wwww.oggo.cf"))).localMd5s(file)
    files.forEach { println(it) }
}*/
