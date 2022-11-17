package cz.chm4.chmtestapp.search.common.bl

data class SearchEntityBl(
    val id: String,
    val name: String,
    val gender: Gender,
    val type: EntityType,
    val sport: Sport,
    val country: String,
    val image: String?,
) {
    companion object {
        val EmptyEntity = SearchEntityBl("", "", Gender.MEN, EntityType.SINGLE_PLAYER, Sport.FOOTBALL, "", null)
    }
}
