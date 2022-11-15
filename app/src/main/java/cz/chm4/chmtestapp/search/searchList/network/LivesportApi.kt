package cz.chm4.chmtestapp.search.searchList.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofit = Retrofit.Builder().baseUrl("https://s.livesport.services/api/v2/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

val liveSportApi = retrofit.create(LivesportSearchApi::class.java)