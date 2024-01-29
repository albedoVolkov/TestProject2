package com.albedo.testproject2.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.albedo.testproject2.R
import com.albedo.testproject2.data.model.ItemMainUIState
import com.albedo.testproject2.databinding.ActivityMainBinding
import com.albedo.testproject2.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


//У нас есть экран с RecyclerView, на нем есть список в два столбца, изначально 15 элементов.
//
//Требования:
//У элемента отображается его номер и кнопка удалить, которая его удаляет, то есть такая плиточка с номером и кнопкой +
//В системе работает что-то асинхронное, которое раз в 5 секунд добавляет новый элемент на случайную позициию
//Номер элемента все время наращивается +
//Добавления и удаления производятся с анимацией (можно стандартной) +
//Вся эта система поддерживает поворот экрана и продолжает работать после него +
//В вертикальном положении сделать две колонки, в горизонтальном четыре



//Требования посложнее:
//Сделать пулл номеров удаленных элементов и новые добавлять из пула, и если там пусто просто наращивать номер

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var adapter: ItemMainAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        viewModel.startService()
    }


    private fun <T> views(block : ActivityMainBinding.() -> T): T? = binding.block()


    private fun init(){
        setAdapter()
        requireData()
    }



    private fun  requireData() {

        viewModel.data.onEach {
            Log.d(TAG, "mainItems : $it")
            viewModel.setListInViewModel(it)
            setItemListInRecyclerView(it)
        }.launchIn(viewModel.viewModelScope)
    }


    private fun setAdapter() {
        views {
            adapter = ItemMainAdapter(this@MainActivity)
            recyclerView1MainActivity.layoutManager = GridLayoutManager(this@MainActivity, resources.getInteger(R.integer.column_rv_1), LinearLayoutManager.VERTICAL, false)
            recyclerView1MainActivity.adapter = adapter


            adapter.onClickListener = object : ItemMainAdapter.OnClickListener {

                override fun onClick(itemData: ItemMainUIState) {
                    viewModel.deleteItem(itemData)
                }

            }

        }
    }


    private fun setItemListInRecyclerView(list: List<ItemMainUIState>) {
        Log.d(TAG, "setItemListInRecyclerView : list - $list")
        adapter.setData(list)
    }

    override fun onDestroy() {
        viewModel.stopService()
        super.onDestroy()
    }

}