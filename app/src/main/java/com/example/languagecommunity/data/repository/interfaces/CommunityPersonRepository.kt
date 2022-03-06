package com.example.languagecommunity.data.repository.interfaces

import androidx.lifecycle.LiveData
import com.example.languagecommunity.data.model.CommunityPerson
import com.example.languagecommunity.data.network.ApiResponse

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */

interface CommunityPersonRepository {
    suspend fun updatePersonLike(id: Int, value: Boolean)
    fun getAllPersons(): LiveData<List<CommunityPerson>>
    suspend fun fetchPersonList(): ApiResponse<String>
}