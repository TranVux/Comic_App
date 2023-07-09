package com.example.assignment.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.assignment.uis.fragments.OptionBottomSheetDialog.OptionBottomSheetType
import com.example.assignment.models.Category

class DiffUtilOptionAdapter(
    private val newList: ArrayList<*>,
    private val oldList: ArrayList<*>,
    private val type: OptionBottomSheetType
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (type === OptionBottomSheetType.CATEGORY) {
            (newList[newItemPosition] as Category).title == (oldList[oldItemPosition] as Category).title
        } else {
            newList[newItemPosition] as String == oldList[oldItemPosition] as String
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (type === OptionBottomSheetType.CATEGORY) {
            (newList[newItemPosition] as Category).title == (oldList[oldItemPosition] as Category).title && (newList[newItemPosition] as Category).thumbnail == (oldList[oldItemPosition] as Category).thumbnail && (newList[newItemPosition] as Category).id == (oldList[oldItemPosition] as Category).id && (newList[newItemPosition] as Category).displayColor == (oldList[oldItemPosition] as Category).displayColor
        } else {
            newList[newItemPosition] as String == oldList[oldItemPosition] as String
        }
    }
}