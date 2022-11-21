package cz.chm4.chmtestapp.search.searchList.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultsDao {

    @Insert
    suspend fun insert(items: List<SearchEntityRoom>)

    @Query("DELETE FROM searchResults")
    suspend fun deleteAll()

    @Query("SELECT * FROM searchResults")
    fun getAll(): Flow<List<SearchEntityRoom>>
}