package com.example.minerva.data.local

import androidx.room.TypeConverter
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.Source
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun  listArticlesToJson (value:List<Article>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToArticleList(value: String?): List<Article>? {
        value?.let {
            return Gson().fromJson(value, Array<Article>::class.java)?.toList()
        }
        return emptyList()
    }

    @TypeConverter
    fun objectSourceToJson(value: Source) = Gson().toJson(value)

    @TypeConverter
    fun jsonToObject(value: String?): Source = Gson().fromJson(value, Source::class.java)


}