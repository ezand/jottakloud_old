package no.ezand.jottakloud

import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.FileInputStream

object Digester {
    fun digestFiles(path: File, digests: Map<File, String> = mapOf()): Map<File, String> {
        if (path.isDirectory) {
            return path.listFiles().fold(digests) { d, p -> digestFiles(p, d) }
        } else {
            val md5hex = DigestUtils.md5Hex(FileInputStream(path))
            return digests + Pair(path, md5hex)
        }
    }
}

fun main(args: Array<String>) {
    print(Digester.digestFiles(File("/Users/eirik/Jottacloud")))
}
