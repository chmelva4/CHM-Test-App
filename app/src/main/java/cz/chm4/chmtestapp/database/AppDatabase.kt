package cz.chm4.chmtestapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.chm4.chmtestapp.search.common.data.database.SearchEntityRoom
import cz.chm4.chmtestapp.search.searchList.data.database.SearchResultsDao
import cz.chm4.chmtestapp.search.searchDetail.data.database.SearchDetailDao

@Database(entities = [
    SearchEntityRoom::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun searchResultsDao(): SearchResultsDao
    abstract fun searchDetailDao(): SearchDetailDao
}