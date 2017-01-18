package no.ezand.jottakloud.data

data class MountPoint(val name: String,
                      val size: Long,
                      val modified: String?,
                      val path: String?,
                      val abspath: String?,
                      val device: String?,
                      val user: String?)
