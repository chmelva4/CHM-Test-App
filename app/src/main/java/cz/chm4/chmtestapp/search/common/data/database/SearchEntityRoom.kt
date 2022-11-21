package cz.chm4.chmtestapp.search.common.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchResults")
data class SearchEntityRoom(
    @PrimaryKey val id: String,
    val name: String,
    val gender: String,
    val type: String,
    val sport: String,
    val country: String,
    val image: String?,
)
