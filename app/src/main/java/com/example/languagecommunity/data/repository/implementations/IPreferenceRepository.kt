package com.example.languagecommunity.data.repository.implementations

import android.content.SharedPreferences
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository

/**
 * Created by Imran Chowdhury on 3/5/2022.
 */

class IPreferenceRepository (
    private val mSharedPreferences: SharedPreferences
): PreferenceRepository {

    companion object {
        private const val SP_Last_FETCHED_PAGE_NO = "SP_Last_FETCHED_PAGE_NO"
    }

    private fun getIntFromSp(code: String):Int {
        return mSharedPreferences.getInt(code, 0)
    }

    private fun setIntToSp(code: String, value: Int) {
        val e = mSharedPreferences.edit()
        e.putInt(code, value)
        e.apply()
    }

    override fun getLastFetchedPage(): Int {
        return getIntFromSp(SP_Last_FETCHED_PAGE_NO)
    }

    override fun setLastFetchedPage(pageNo: Int) {
        setIntToSp(SP_Last_FETCHED_PAGE_NO, pageNo)
    }

}