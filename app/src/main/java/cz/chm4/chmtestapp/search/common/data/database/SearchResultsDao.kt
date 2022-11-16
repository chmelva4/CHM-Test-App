package cz.chm4.chmtestapp.search.common.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultsDao {

    @Insert
    suspend fun insert(items: List<SearchEntityRoom>)

    @Query("delete from searchResults")
    suspend fun deleteAll()

    @Query("select * from searchResults")
    fun getAll(): Flow<List<SearchEntityRoom>>
}