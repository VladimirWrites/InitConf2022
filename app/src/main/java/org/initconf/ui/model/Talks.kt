package org.initconf.ui.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Talks(
    @Json(name = "talks")
    val listOfTalks: List<Talk>
)
