package com.albedo.testproject2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "items")
data class ItemMainUIState (
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id : String,

    @SerializedName("number")
    @ColumnInfo(name = "number")
    var number : Long,

    ){
        override fun toString(): String {
            return Gson().toJson(this,ItemMainUIState::class.java)
        }
    }

    fun fromStringToItemMain(string: String): ItemMainUIState? {
        return try {
            Gson().fromJson(string, ItemMainUIState::class.java)
        }catch(e : Exception){
            null
        }
    }