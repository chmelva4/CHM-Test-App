package cz.chm4.chmtestapp.search.common.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface LivesportSearchApi {

    @GET("search")
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("type-ids") typeIds: String = "1,2,3,4",
        @Query("sport-ids") sportIds: String = "1,2,3,4,5,6,7,8,9",
        @Query("lang-id") langId: String = "1",
        @Query("project-id") projectId: String = "602",
        @Query("project-type-id") projectTypeId: String = "1",
    ): List<SearchEntity>
}
