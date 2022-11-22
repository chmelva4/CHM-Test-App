package cz.chm4.chmtestapp.search.common.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchEntity(
    val id: String,
    val name: String,
    val gender: IdObject,
    val type: IdObject,
    val sport: IdObject,
    val defaultCountry: IdObject,
    val images: List<ImageObject>
)
