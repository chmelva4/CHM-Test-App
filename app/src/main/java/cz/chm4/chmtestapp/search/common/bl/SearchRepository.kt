package cz.chm4.chmtestapp.search.common.bl

import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.searchList.ui.SearchFilter

class SearchRepository(
    private val livesportSearchApi: LivesportSearchApi
) {


    suspend fun search(searchQuery: String): List<SearchEntityBl> {
        return livesportSearchApi.search(searchQuery).map { it.toSearchEntityBl() }
    }
}