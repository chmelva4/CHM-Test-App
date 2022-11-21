package cz.chm4.chmtestapp.search.di

import android.content.Context
import cz.chm4.chmtestapp.database.AppDatabase
import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import cz.chm4.chmtestapp.search.searchDetail.bl.SearchDetailRepository
import cz.chm4.chmtestapp.search.searchDetail.data.database.SearchDetailDao
import cz.chm4.chmtestapp.search.searchList.bl.SearchListRepository
import cz.chm4.chmtestapp.search.searchList.data.database.SearchResultsDao
import cz.chm4.chmtestapp.search.searchList.data.sharedPrefs.SearchListPrefManager
import cz.chm4.chmtestapp.search.searchList.data.sharedPrefs.SearchListSharedPrefConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SearchModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://s.livesport.services/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): LivesportSearchApi = retrofit.create(LivesportSearchApi::class.java)

    @Singleton
    @Provides
    fun provideSearchResultsDao(database: AppDatabase): SearchResultsDao = database.searchResultsDao()

    @Singleton
    @Provides
    fun provideSearchRepository(livesportSearchApi: LivesportSearchApi, searchResultsDao: SearchResultsDao): SearchListRepository = SearchListRepository(livesportSearchApi, searchResultsDao)

    @Singleton
    @Provides
    fun provideSearchListPrefManager(@ApplicationContext context: Context) = SearchListPrefManager(SearchListSharedPrefConstants.FILE_NAME ,context)

    @Singleton
    @Provides
    fun provideSearchDetailDao(database: AppDatabase) = database.searchDetailDao()

    @Singleton
    @Provides
    fun provideSearchDetailRepository(searchDetailDao: SearchDetailDao) = SearchDetailRepository(searchDetailDao)
}
