package com.example.languagecommunity

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.languagecommunity.data.db.AppDb
import com.example.languagecommunity.data.repository.implementations.IPreferenceRepository
import com.example.languagecommunity.data.repository.interfaces.PreferenceRepository
import com.example.languagecommunity.utills.Constants
import com.facebook.stetho.Stetho

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */

class LanguageCommunityApp : Application() {

    companion object {
        lateinit var appDb: AppDb
        lateinit var mPrefRepo: PreferenceRepository
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        appDb = Room.databaseBuilder(this, AppDb::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

        mPrefRepo = IPreferenceRepository(
            getSharedPreferences(
                Constants.PREF_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}