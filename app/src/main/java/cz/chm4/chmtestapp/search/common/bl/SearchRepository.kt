package cz.chm4.chmtestapp.search.common.bl

import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import cz.chm4.chmtestapp.search.common.data.database.SearchResultsDao
import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.searchList.ui.SearchFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepository(
    private val livesportSearchApi: LivesportSearchApi,
    private val searchResultsDao: SearchResultsDao
) {


    suspend fun search(searchQuery: String) {
        val data =  livesportSearchApi.search(searchQuery).map { it.toSearchEntityBl() }
        searchResultsDao.deleteAll()
        searchResultsDao.insert(data.map { SearchEntityRoom.fromSearchEntityBl(it) })
    }

    fun getAll(): Flow<List<SearchEntityBl>> = searchResultsDao.getAll().map { it.map { item-> item.toSearchEntityBl()} }
}