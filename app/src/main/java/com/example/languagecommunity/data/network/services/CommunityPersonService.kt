package com.example.languagecommunity.data.network.services

import com.example.languagecommunity.data.model.CommunityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */
interface CommunityPersonService {

    @GET("api/community_{pageNumber}.json")
    suspend fun getCommunityMembers(
        @Path("pageNumber") pageNumber: Int
    ): Response<CommunityResponse>

}