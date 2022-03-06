package com.example.languagecommunity.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.languagecommunity.data.db.dao.communityPersonDao.CommunityPersonDao
import com.example.languagecommunity.data.db.typeConverters.ListTypeConverter
import com.example.languagecommunity.data.model.CommunityPerson

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */

@Database (
    entities = [
        CommunityPerson::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters (
    ListTypeConverter::class
)
abstract class AppDb: RoomDatabase() {
    abstract fun personsDao(): CommunityPersonDao
}