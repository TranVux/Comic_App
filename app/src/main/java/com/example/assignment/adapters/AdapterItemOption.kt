package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapters.AdapterItemOption.ItemOptionViewHolder
import com.example.assignment.databinding.ItemOptionLayoutBinding
import com.example.assignment.fragments.OptionBottomSheetDialog.OptionBottomSheetType
import com.example.assignment.models.Category
import com.example.assignment.utils.DiffUtilOptionAdapter

class AdapterItemOption(
    private var listData: ArrayList<*>,
    private val type: OptionBottomSheetType,
    private var itemSelected: Int,
    private val itemOptionHandler: ItemOptionHandler
) : RecyclerView.Adapter<ItemOptionViewHolder>() {

    fun setListData(listData: ArrayList<*>, type: OptionBottomSheetType) {
        val diffResult =
            DiffUtil.calculateDiff(DiffUtilOptionAdapter(listData, this.listData, type))
        diffResult.dispatchUpdatesTo(this)
        this.listData.clear()
        this.listData = listData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemSelected(position: Int) {
        itemSelected = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemOptionViewHolder {
        val itemOptionLayoutBinding =
            ItemOptionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemOptionViewHolder(itemOptionLayoutBinding)
    }

    override fun getItemCount(): Int {
        return listData.size ?: 0
    }

    override fun onBindViewHolder(
        holder: ItemOptionViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (listData[position] == null) return
        var valueString: String? = ""
        if (type === OptionBottomSheetType.CATEGORY) {
            valueString = (listData[position] as Category).title
        } else if (type === OptionBottomSheetType.COUNTRY) {
            valueString = listData[position] as String
        }

        holder.itemOptionLayoutBinding.apply {
            textOption.text = valueString
            itemOption.setCardBackgroundColor(
                if (position == itemSelected) Color.parseColor("#00AAD3") else Color.parseColor("#1E2027")
            )
//        val finalValueString = valueString
            itemOption.setOnClickListener {
                valueString?.let { string ->
                    itemOptionHandler.onItemClick(
                        string,
                        position
                    )
                }
            }
        }

    }

    class ItemOptionViewHolder(val itemOptionLayoutBinding: ItemOptionLayoutBinding) :
        RecyclerView.ViewHolder(
            itemOptionLayoutBinding.root
        )

    interface ItemOptionHandler {
        fun onItemClick(value: String, index: Int)
    }
}
