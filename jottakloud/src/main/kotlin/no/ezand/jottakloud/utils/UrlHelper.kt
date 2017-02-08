package no.ezand.jottakloud.utils

object UrlHelper {
    private val urlPattern = """^((http[s]?):/)?/?([^:/\s]+)((/\w+)*/)([\w\-.]+[^#?\s]+)(.*)?(#[\w\-]+)?$""".toRegex()
    private val urlPattern2 = """^(http[s])""".toRegex()

    fun contentePath(basePath: String, path: String): String {
        println(basePath + ": " + urlPattern.matches(basePath))
        println(urlPattern2.matchEntire(basePath)?.groups?.forEach(::println))

        val pathWithoutBase = path.replace(basePath, "")
        return pathWithoutBase
    }
}

fun main(args: Array<String>) {
    println("FOOO: " + UrlHelper.contentePath("https://www.foo.com/jfs", "/2017/22"))
}