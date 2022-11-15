package cz.chm4.chmtestapp.search.searchList.bl

import cz.chm4.chmtestapp.search.searchList.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.searchList.ui.SearchFilter

class SearchRepository(
    private val livesportSearchApi: LivesportSearchApi
) {


    suspend fun search(searchQuery: String, searchFilter: SearchFilter): List<SearchEntityBl> {
        return livesportSearchApi.search(searchQuery).map { it.toSearchEntityBl() }
    }
}