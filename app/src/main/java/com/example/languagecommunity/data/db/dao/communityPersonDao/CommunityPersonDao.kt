package com.example.languagecommunity.data.db.dao.communityPersonDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.languagecommunity.data.db.dao.BaseDao
import com.example.languagecommunity.data.model.CommunityPerson

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */


@Dao
abstract class CommunityPersonDao: BaseDao<CommunityPerson>() {

    @Query("Delete FROM persons")
    abstract fun deleteAll()

    @Query("SELECT * from persons")
    abstract fun getAllPersons(): LiveData<List<CommunityPerson>>

    @Query("UPDATE persons set isLiked = :value WHERE id = :id")
    abstract fun updatePersonLike(id: Int, value: Boolean)

}