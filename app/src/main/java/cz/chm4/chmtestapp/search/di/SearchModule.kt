package cz.chm4.chmtestapp.search.di

import cz.chm4.chmtestapp.database.AppDatabase
import cz.chm4.chmtestapp.search.common.bl.SearchRepository
import cz.chm4.chmtestapp.search.common.data.database.SearchResultsDao
import cz.chm4.chmtestapp.search.common.data.network.LivesportSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideSearchRepository(livesportSearchApi: LivesportSearchApi, searchResultsDao: SearchResultsDao): SearchRepository = SearchRepository(livesportSearchApi, searchResultsDao)


}