package com.albedo.testproject2.viewModels



import android.util.Log
import androidx.lifecycle.ViewModel
import com.albedo.testproject2.data.model.ItemMainUIState
import com.albedo.testproject2.utils.ItemFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val factory: ItemFactory) :
    ViewModel()  {

    private val TAG = "MainActivityViewModel"


    private var job: Job? = null

    //ITEMS
    private var _data: MutableStateFlow<List<ItemMainUIState>> = MutableStateFlow(listOf())
    val data: MutableStateFlow<List<ItemMainUIState>> get() = _data

    private var _mainItems : List<ItemMainUIState> = listOf()
    val mainItems: List<ItemMainUIState> get() = _mainItems// this list isn't for showing and not sorted


    private var number : Long = 0

    init{
        var count = 0
        while (true) {
            if (count <= 15) {
                count += 1
                addItem(false)
            } else {
                break
            }
        }
    }


    fun startService(){
        Log.d(TAG, "service is running")
        job = MainScope().launch {
            while (true) {
                async { addItem(true)}.await()
                delay(5000)
            }
        }
    }

    fun stopService() {
        Log.d(TAG,"service is stopped")
        job?.cancel()
        job = null
    }

    fun setListInViewModel(list : List<ItemMainUIState>) {
        Log.d(TAG, "setListInViewModel : list - $list")
        _mainItems = list
    }

    private fun addItem(isRandom : Boolean){
        number += 1
        val oldList = _data.value.toMutableList()
        if(isRandom) {
            (oldList.size-1)
            val positionR = (0 until (oldList.size-1)).random()
            oldList.add(positionR,factory.createNewItemMain(number))
        }else{
            oldList.add(factory.createNewItemMain(number))
        }
        data.value = oldList
    }

    fun deleteItem(item: ItemMainUIState) {
        val oldList = _data.value.toMutableList()
        oldList.remove(item)
        data.value = oldList
    }

    private fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

    override fun onCleared() {
        _data.value = listOf()
        _mainItems = listOf()
        number = 0
        super.onCleared()
    }

}