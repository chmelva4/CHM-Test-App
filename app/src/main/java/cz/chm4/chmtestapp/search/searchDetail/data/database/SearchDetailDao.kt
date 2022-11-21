package cz.chm4.chmtestapp.search.searchDetail.data.database

import androidx.room.Dao
import androidx.room.Query
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDetailDao {

    @Query("SELECT * FROM searchResults WHERE id == :id")
    fun getSearchEntityById(id: String): Flow<SearchEntityRoom>
}