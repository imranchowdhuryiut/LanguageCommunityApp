package com.example.languagecommunity.di

import com.example.languagecommunity.LanguageCommunityApp
import com.example.languagecommunity.data.db.dao.communityPersonDao.CommunityPersonDao
import com.example.languagecommunity.data.network.ApiClient.createApiService
import com.example.languagecommunity.data.network.services.CommunityPersonService
import com.example.languagecommunity.data.repository.implementations.ICommunityPersonRepository
import com.example.languagecommunity.data.repository.interfaces.CommunityPersonRepository
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository

/**
 * Created by Imran Chowdhury on 3/5/2022.
 */
object Injection {

    private fun provideCommunityService(): CommunityPersonService {
        return createApiService()
    }

    private fun provideCommunityPersonDao(): CommunityPersonDao {
        return LanguageCommunityApp.appDb.personsDao()
    }

    private fun providePreferenceRepository(): PreferenceRepository {
        return LanguageCommunityApp.mPrefRepo
    }

    fun provideCommunityRepository(): CommunityPersonRepository {
        return ICommunityPersonRepository(
            provideCommunityService(),
            provideCommunityPersonDao(),
            providePreferenceRepository()
        )
    }


}