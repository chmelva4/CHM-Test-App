package cz.chm4.chmtestapp.search.common.data.network

data class SearchEntity(
    val id: String,
    val name: String,
    val gender: IdObject,
    val type: IdObject,
    val sport: IdObject,
    val defaultCountry: IdObject,
    val images: List<ImageObject>
) {
    companion object {
        const val BASE_IMG_URL = "https://www.livesport.cz/res/image/data/"
    }
}
