package cz.chm4.chmtestapp.search.common.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageObject(
    val path: String,
    val usageId: Int,
    val variantTypeId: Int
)
