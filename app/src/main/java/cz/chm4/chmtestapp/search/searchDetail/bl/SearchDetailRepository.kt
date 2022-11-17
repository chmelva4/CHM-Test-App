package cz.chm4.chmtestapp.search.searchDetail.bl

import cz.chm4.chmtestapp.search.common.bl.SearchEntityBl
import cz.chm4.chmtestapp.search.common.bl.toSearchEntityBl
import cz.chm4.chmtestapp.search.searchDetail.data.database.SearchDetailDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchDetailRepository(
    private val searchDetailDao: SearchDetailDao
) {

    fun getSearchEntityById(id: String): Flow<SearchEntityBl> = searchDetailDao.getSearchEntityById(id).map { it.toSearchEntityBl() }
}