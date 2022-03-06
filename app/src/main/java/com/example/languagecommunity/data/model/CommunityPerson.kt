package com.example.languagecommunity.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "persons")
data class CommunityPerson(
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("learns")
    var learns: List<String>? = null,
    @SerializedName("natives")
    var natives: List<String>? = null,
    @SerializedName("pictureUrl")
    var pictureUrl: String? = null,
    @SerializedName("referenceCnt")
    var referenceCnt: Int? = null,
    @SerializedName("topic")
    var topic: String? = null,
    var isLiked: Boolean = false
)