package org.initconf.ui.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Talk(
    @Json(name = "title")
    val title: String,
    @Json(name = "speaker")
    val speaker: Speaker
)
