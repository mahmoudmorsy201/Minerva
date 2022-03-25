package com.example.minerva.data.local

import androidx.room.TypeConverter
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.Source
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun objectSourceToJson(value: Source) = Gson().toJson(value)

    @TypeConverter
    fun jsonToObjectSource(value: String?): Source = Gson().fromJson(value, Source::class.java)


}