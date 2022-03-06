package com.example.languagecommunity.data.db.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.annotation.Nullable


/**
 * Created by Imran Chowdhury on 3/1/2022.
 */

class ListTypeConverter {
    @TypeConverter
    @Nullable
    fun fromString(value: String?): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return if (value == null) {
            mutableListOf()
        } else {
            Gson().fromJson(value, type)
        }
    }

    @TypeConverter
    @Nullable
    fun fromList(data: List<String>?): String {
        if (data == null) {
            return ""
        }
        return Gson().toJson(data)
    }
}