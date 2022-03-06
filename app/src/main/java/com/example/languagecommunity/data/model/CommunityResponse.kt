package com.example.languagecommunity.data.model


import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    @SerializedName("errorCode")
    var errorCode: Any? = null,
    @SerializedName("response")
    var response: List<CommunityPerson>? = null,
    @SerializedName("type")
    var type: String? = null
)