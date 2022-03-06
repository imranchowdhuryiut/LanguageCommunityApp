package com.example.languagecommunity.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.languagecommunity.data.model.CommunityPerson
import com.example.languagecommunity.data.network.ApiResponse
import com.example.languagecommunity.data.network.Resource
import com.example.languagecommunity.data.repository.implementations.ICommunityPersonRepository
import com.example.languagecommunity.data.repository.interfaces.CommunityPersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */
class HomeViewModel(
    private val mRepo: CommunityPersonRepository
): ViewModel() {

    fun fetchPersonList(): LiveData<Resource<ApiResponse<String>>> {
        return requestApiData {
            mRepo.fetchPersonList()
        }
    }

    fun getSavedPersonList(): LiveData<List<CommunityPerson>> {
        return mRepo.getAllPersons()
    }

    fun updatePersonLike(id: Int, value: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            mRepo.updatePersonLike(id, value)
        }
    }

    private fun <T> requestApiData(
        processData: ((T?) -> Unit)? = null,
        requestApiResponse: suspend () -> ApiResponse<T>?
    ): LiveData<Resource<ApiResponse<T>>> {
        return liveData(Dispatchers.Default) {
            emit(Resource.loading())
            val data = requestApiResponse()
            if (data?.status == true) {
                processData?.invoke(data.data)
                emit(Resource.success(data))
            } else emit(Resource.error(data?.message, data))
        }
    }

}