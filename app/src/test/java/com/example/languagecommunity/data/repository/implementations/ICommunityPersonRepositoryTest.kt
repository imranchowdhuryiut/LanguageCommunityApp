package com.example.languagecommunity.data.repository.implementations

import com.example.languagecommunity.data.db.dao.communityPersonDao.CommunityPersonDao
import com.example.languagecommunity.data.model.CommunityResponse
import com.example.languagecommunity.data.network.ApiResponse
import com.example.languagecommunity.data.network.services.CommunityPersonService
import com.example.languagecommunity.data.repository.interfaces.CommunityPersonRepository
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import retrofit2.Response
import java.net.SocketTimeoutException

/**
 * Created by Imran Chowdhury on 3/5/2022.
 */
@RunWith(JUnit4::class)
class ICommunityPersonRepositoryTest : TestCase() {

    private lateinit var repository: CommunityPersonRepository
    private lateinit var mWebService: CommunityPersonService
    private lateinit var mPersonDao: CommunityPersonDao
    private lateinit var mPrefRepo: PreferenceRepository

    @Before
    fun setup() {
        mPersonDao = Mockito.mock(CommunityPersonDao::class.java)
        mPrefRepo = Mockito.mock(PreferenceRepository::class.java)
        mWebService = Mockito.mock(CommunityPersonService::class.java)
        repository = ICommunityPersonRepository(
            mWebService,
            mPersonDao,
            mPrefRepo
        )
    }

    @Test
    fun `test not null object`() {
        assertNotNull(mPrefRepo)
        assertNotNull(mWebService)
        assertNotNull(repository)
        assertNotNull(mPersonDao)
    }

    @Test
    fun `test repository get person calls DB`() {
        repository.getAllPersons()
        Mockito.verify(mPersonDao).getAllPersons()
    }

    @Test
    fun `test updatePersonLike calls db with same parameter`() {
        val id = 105146
        val value = false
        runBlocking {
            repository.updatePersonLike(id, value)
        }
        Mockito.verify(mPersonDao).updatePersonLike(id, value)
    }

    @Test
    fun `test fetchPersonList calls pref repo to get last fetched page`() {
        runBlocking {
            repository.fetchPersonList()
        }
        Mockito.verify(mPrefRepo).getLastFetchedPage()
    }

    @Test
    fun `test if last fetched page is 4 fetchPersonList returns No data found` () {
        Mockito.`when`(mPrefRepo.getLastFetchedPage()).thenReturn(4)
        var response: ApiResponse<String>? = null
        runBlocking {
            response = repository.fetchPersonList()
        }
        assertEquals(false, response?.status)
        assertEquals("No data found", response?.message)
    }

    @Test
    fun `test if last fetched page is 0 fetchPersonList calls fetch API`() {
        Mockito.`when`(mPrefRepo.getLastFetchedPage()).thenReturn(0)
        runBlocking {
            repository.fetchPersonList()
            Mockito.verify(mWebService).getCommunityMembers(1)
        }
    }

    @Test
    fun `test call fetchPersonList should return success`() {
        runBlocking {
            Mockito.`when`(mPrefRepo.getLastFetchedPage()).thenReturn(0)
            Mockito.`when`(mWebService.getCommunityMembers(1)).thenReturn(
                Response.success(200, CommunityResponse(
                    errorCode = null,
                    type = "test",
                    response = null
                ))
            )
            val response = repository.fetchPersonList()
            Mockito.verify(mWebService).getCommunityMembers(1)
            Mockito.verify(mPrefRepo).setLastFetchedPage(1)
            assertEquals(true, response.status)
        }
    }

    @Test
    fun `test call fetchPersonList should return failure`() {
        runBlocking {
            Mockito.`when`(mPrefRepo.getLastFetchedPage()).thenReturn(0)
            Mockito.`when`(mWebService.getCommunityMembers(1)).thenReturn(
                Response.error(404, ResponseBody.create(
                    MediaType.parse("application/json"),
                    ""
                ))
            )
            val response = repository.fetchPersonList()
            Mockito.verify(mWebService).getCommunityMembers(1)
            Mockito.verify(mPrefRepo, times(0)).setLastFetchedPage(1)
            assertEquals(false, response.status)
        }
    }
}