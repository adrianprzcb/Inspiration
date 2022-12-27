package com.adrian.inspiration_aquoteeveryhour

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getQuote(@Url url:String): Response<InspirationResponse>

}