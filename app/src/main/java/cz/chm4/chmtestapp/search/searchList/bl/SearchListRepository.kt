package cz.chm4.chmtestapp.search.searchList.bl

import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.fromSearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.toSearchEntityBl
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.searchList.data.database.SearchResultsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchListRepository(
    private val livesportSearchApi: LivesportSearchApi,
    private val searchResultsDao: SearchResultsDao
) {

    suspend fun search(searchQuery: String) {
        val data = livesportSearchApi.search(searchQuery).map { it.toSearchEntityBl() }
        searchResultsDao.deleteAll()
        searchResultsDao.insert(data.map { fromSearchEntityBl(it) })
    }

    fun getAll(): Flow<List<SearchEntityBl>> = searchResultsDao.getAll().map { it.map { item -> item.toSearchEntityBl() } }
}
