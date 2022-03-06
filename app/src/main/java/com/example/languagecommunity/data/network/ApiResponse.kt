package com.example.languagecommunity.data.network

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */
data class ApiResponse<T>(
    val status: Boolean,
    val message: String?,
    val data: T? = null,
)