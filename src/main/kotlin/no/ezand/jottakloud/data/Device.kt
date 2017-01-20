package no.ezand.jottakloud.data

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime
import java.util.*

data class Device(val name: String,
                  @JsonProperty("display_name") val displayName: String,
                  val type: String,
                  val sid: UUID,
                  val size: Long,
                  val modified: DateTime?,
                  val user: String?,
                  @JsonProperty("mountPoints") val mountPoints: List<MountPoint>?)
