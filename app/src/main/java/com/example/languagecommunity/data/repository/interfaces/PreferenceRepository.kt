package com.example.languagecommunity.data.repository.interfaces

/**
 * Created by Imran Chowdhury on 3/5/2022.
 */
interface PreferenceRepository {

    fun getLastFetchedPage(): Int
    fun setLastFetchedPage(pageNo: Int)

}