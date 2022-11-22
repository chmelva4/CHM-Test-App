package cz.chm4.chmtestapp.search.common.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IdObject(
    val id: Int,
    val name: String
)
