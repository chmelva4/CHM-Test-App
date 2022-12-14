package cz.chm4.chmtestapp.search.searchList.ui

import cz.chm4.chmtestapp.search.common.bl.EntityType
import cz.chm4.chmtestapp.search.common.bl.Sport

data class SearchListEntity(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val sport: Sport,
    val type: EntityType
)
