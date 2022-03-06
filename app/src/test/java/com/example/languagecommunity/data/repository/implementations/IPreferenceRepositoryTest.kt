package com.example.languagecommunity.data.repository.implementations

import androidx.test.core.app.ApplicationProvider
import com.example.languagecommunity.LanguageCommunityApp
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.robolectric.RobolectricTestRunner


/**
 * Created by Imran Chowdhury on 3/5/2022.
 */

@RunWith(RobolectricTestRunner::class)
class IPreferenceRepositoryTest : TestCase() {

    private lateinit var mPrefRepo: PreferenceRepository

    @Before
    fun testSetUp() {
        mPrefRepo = IPreferenceRepository(
            ApplicationProvider.getApplicationContext<LanguageCommunityApp>().getSharedPreferences(
                anyString(),
                anyInt()
            )
        )
    }

    @Test
    fun `test not null object`() {
        assertNotNull(mPrefRepo)
    }

    @Test
    fun `test if not set LastFetchedPage should return 0` () {
        val value = mPrefRepo.getLastFetchedPage()
        assertEquals(0, value)
    }

    @Test
    fun `test if set LastFetchedPage should return set value` () {
        val setValue = 20
        mPrefRepo.setLastFetchedPage(20)
        val getValue = mPrefRepo.getLastFetchedPage()
        assertEquals(setValue, getValue)
    }

}