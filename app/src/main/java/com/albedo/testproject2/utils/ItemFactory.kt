package com.albedo.testproject2.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.albedo.testproject2.data.model.ItemMainUIState
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ItemFactory @Inject constructor(){

    private val TAG = "ItemFactory"

    private var countId = "0"

    fun createNewItemMain(number : Long): ItemMainUIState {
        countId = (countId.toInt() + 1).toString()
        return ItemMainUIState(countId, number)
    }
}