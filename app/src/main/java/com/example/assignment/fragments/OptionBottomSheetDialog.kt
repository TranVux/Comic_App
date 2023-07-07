package com.example.assignment.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.assignment.R
import com.example.assignment.adapters.AdapterItemOption
import com.example.assignment.databinding.OptionBottomSheetLayoutBinding
import com.example.assignment.utils.GridSpacingItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionBottomSheetDialog : BottomSheetDialogFragment() {
    private var optionBottomSheetLayoutBinding: OptionBottomSheetLayoutBinding? = null
    private var titleText: String? = null
    private var selectedItem = 0
    private lateinit var dataList: ArrayList<*>
    private lateinit var optionBottomSheetType: OptionBottomSheetType
    private var adapterItemOption: AdapterItemOption? = null
    private var handlerDismissListener: HandlerDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        val data = arguments
        if (data != null) {
            dataList = data.getSerializable("DATA_LIST") as ArrayList<*>
            optionBottomSheetType = data.getSerializable("OPTION_TYPE") as OptionBottomSheetType
            titleText = data.getString("TITLE")
            selectedItem = data.getInt("DEFAULT_SELECTED_ITEM_INDEX")
        }
    }

    fun setHandlerDismissListener(handlerDismissListener: HandlerDismissListener?): OptionBottomSheetDialog {
        this.handlerDismissListener = handlerDismissListener
        return this;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        optionBottomSheetLayoutBinding =
            OptionBottomSheetLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(optionBottomSheetLayoutBinding!!.root)
        handleBottomSheet()
        return bottomSheetDialog
    }

    private fun handleBottomSheet() {
        optionBottomSheetLayoutBinding!!.title.text = titleText
        addEventListener()
        handleListOption()
    }

    private fun handleListOption() {
        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        adapterItemOption = AdapterItemOption(
            dataList,
            optionBottomSheetType,
            selectedItem,
            object : AdapterItemOption.ItemOptionHandler {
                override fun onItemClick(value: String, index: Int) {
                    Log.d(TAG, "onItemClick: $value")
                    adapterItemOption!!.setItemSelected(index)
                    selectedItem = index
                }
            })

        optionBottomSheetLayoutBinding!!.listItem.layoutManager = gridLayoutManager
        optionBottomSheetLayoutBinding!!.listItem.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                30,
                false
            )
        )
        optionBottomSheetLayoutBinding!!.listItem.adapter = adapterItemOption
    }

    private fun addEventListener() {
        optionBottomSheetLayoutBinding!!.buttonConfirm.setOnClickListener {
            Log.d(TAG, "onClick: Confirm button")
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        handlerDismissListener!!.onDismiss(
            optionBottomSheetType,
            dataList[selectedItem],
            selectedItem
        )
        super.onDismiss(dialog)
    }

    interface HandlerDismissListener {
        fun onDismiss(type: OptionBottomSheetType?, value: Any?, selectedItemIndex: Int)
    }

    enum class OptionBottomSheetType {
        COUNTRY, CATEGORY
    }

    companion object {
        private val TAG = OptionBottomSheetDialog::class.java.simpleName
        fun newInstance(
            titleText: String?,
            dataList: ArrayList<*>?,
            optionBottomSheetType: OptionBottomSheetType?,
            defaultSelectedItemIndex: Int
        ): OptionBottomSheetDialog {
            val optionBottomSheetDialog = OptionBottomSheetDialog()
            val data = Bundle()
            data.putSerializable("DATA_LIST", dataList)
            data.putString("TITLE", titleText)
            data.putInt("DEFAULT_SELECTED_ITEM_INDEX", defaultSelectedItemIndex)
            data.putSerializable("OPTION_TYPE", optionBottomSheetType)
            optionBottomSheetDialog.arguments = data
            return optionBottomSheetDialog
        }
    }
}