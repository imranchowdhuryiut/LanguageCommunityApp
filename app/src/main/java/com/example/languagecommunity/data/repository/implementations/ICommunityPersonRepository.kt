package com.example.languagecommunity.data.repository.implementations

import androidx.lifecycle.LiveData
import androidx.room.PrimaryKey
import com.example.languagecommunity.LanguageCommunityApp
import com.example.languagecommunity.data.db.dao.communityPersonDao.CommunityPersonDao
import com.example.languagecommunity.data.model.CommunityPerson
import com.example.languagecommunity.data.network.ApiClient.createApiService
import com.example.languagecommunity.data.network.ApiResponse
import com.example.languagecommunity.data.network.Resource
import com.example.languagecommunity.data.network.services.CommunityPersonService
import com.example.languagecommunity.data.repository.interfaces.CommunityPersonRepository
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository
import com.example.languagecommunity.utills.Constants
import java.lang.Exception

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */

class ICommunityPersonRepository(
    private val mWebService: CommunityPersonService,
    private val mPersonDao: CommunityPersonDao,
    private val mPrefRepo: PreferenceRepository
) : CommunityPersonRepository {

    override suspend fun updatePersonLike(id: Int, value: Boolean) {
        mPersonDao.updatePersonLike(id, value)
    }

    override fun getAllPersons(): LiveData<List<CommunityPerson>> {
        return mPersonDao.getAllPersons()
    }

    override suspend fun fetchPersonList(): ApiResponse<String> {
        val pageToFetch = mPrefRepo.getLastFetchedPage() + 1
        if (pageToFetch < Constants.totalPage) {
            return try {
                val response = mWebService.getCommunityMembers(pageToFetch)
                if (response.isSuccessful) {
                    mPrefRepo.setLastFetchedPage(pageToFetch)
                    response.body()?.response?.let { LanguageCommunityApp.appDb.personsDao().save(it) }
                    ApiResponse(true, "", "Success")
                } else {
                    ApiResponse(false, "Something went wrong", "failure")
                }
            } catch (ex: Exception) {
                ApiResponse(false, "Something went wrong", "failure")
            }
        } else {
            return ApiResponse(false, "No data found", "failure")
        }
    }

}