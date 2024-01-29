package com.albedo.testproject2.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albedo.testproject2.data.model.ItemMainUIState
import com.albedo.testproject2.databinding.ItemListBinding

class ItemMainAdapter(private val context: Context) : RecyclerView.Adapter<ItemMainAdapter.ItemViewHolder>() {

    val TAG = "ItemMainAdapter"

    private var data : List<ItemMainUIState> = listOf()

    lateinit var onClickListener: OnClickListener



    inner class ItemViewHolder(private var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ItemMainUIState) {
            binding.containerBtn3ItemList.setOnClickListener {
                onClickListener.onClick(itemModel)
            }
            binding.textView1ItemList.text = itemModel.number.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemListBinding.inflate(itemView, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemId(position: Int) = data[position].id.toLong()

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList : List<ItemMainUIState>){
        Log.d(TAG, "newList $newList")
        data = newList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(itemData: ItemMainUIState)
    }
}