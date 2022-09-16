package org.initconf.ui.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Speaker(
    @Json(name = "name")
    val name: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "image")
    val image: String? = null
)