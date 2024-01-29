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
                addItem()
            } else {
                break
            }
        }
    }


    fun startService(){
        Log.d(TAG, "service is running")
        job = MainScope().launch {
            while (true) {
                async { addItem()}.await()
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

    private fun addItem(){
        number+=1
        val oldList = _data.value.toMutableList()
        oldList.add(factory.createNewItemMain(number))
        data.value = oldList
    }

    fun deleteItem(item: ItemMainUIState) {
        val oldList = _data.value.toMutableList()
        oldList.remove(item)
        data.value = oldList
    }

    override fun onCleared() {
        _data.value = listOf()
        _mainItems = listOf()
        number = 0
        super.onCleared()
    }

}