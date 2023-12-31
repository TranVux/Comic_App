package com.example.assignment.uis.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.example.assignment.databinding.ActivityDetailBinding
import com.example.assignment.uis.fragments.OptionBottomSheetDialog
import com.example.assignment.uis.fragments.OptionBottomSheetDialog.OptionBottomSheetType
import com.example.assignment.models.Category

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private val activityDetailBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private var defaultSelectedItemCategoryIndex = 0
    private var defaultSelectedItemCountryIndex = 0
    private var textCategorySelected = ""
    private var textCountrySelected = ""
    private var textDecoration = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityDetailBinding.root)
        init()
        addEventListener()
    }

    fun init() {}
    private fun addEventListener() {
        activityDetailBinding.buttonCountry.setOnClickListener(this@DetailActivity)
        activityDetailBinding.buttonCategory.setOnClickListener(this@DetailActivity)
    }

    private fun handleBottomSheetOption(type: OptionBottomSheetType) {
        val optionBottomSheetDialog = OptionBottomSheetDialog
            .newInstance(
                if (type == OptionBottomSheetType.COUNTRY) "Quốc gia" else "Thể loại",
                if (type == OptionBottomSheetType.COUNTRY) countries else ArrayList(),
                type,
                if (type == OptionBottomSheetType.COUNTRY) defaultSelectedItemCountryIndex else defaultSelectedItemCategoryIndex
            ).setHandlerDismissListener(object :
                OptionBottomSheetDialog.HandlerDismissListener {
                override fun onDismiss(
                    type: OptionBottomSheetType?,
                    value: Any?,
                    selectedItemIndex: Int
                ) {
                    if (type == OptionBottomSheetType.CATEGORY) {
                        defaultSelectedItemCategoryIndex = selectedItemIndex
                        Log.d(TAG, "onDismiss: " + (value as Category).title)
                        handleOption(type, value)
                    } else if (type == OptionBottomSheetType.COUNTRY) {
                        defaultSelectedItemCountryIndex = selectedItemIndex
                        handleOption(type, value)
                        Log.d(TAG, "onDismiss: " + value as String)
                    }
                }
            })

        optionBottomSheetDialog.show(supportFragmentManager, optionBottomSheetDialog.tag)
    }

    @SuppressLint("SetTextI18n")
    private fun handleOption(type: OptionBottomSheetType, value: Any?) {
        if (type == OptionBottomSheetType.CATEGORY) {
            if ((value as Category).title.toString() != "Mặc định") {
                textCategorySelected = (value as Category).title.toString();
            } else {
                textCategorySelected = ""
            }
        } else if (type == OptionBottomSheetType.COUNTRY) {
            if (value.toString() != "Mặc định") {
                textCountrySelected = value.toString()
            } else {
                textCountrySelected = ""
            }
        }

        if (textCountrySelected == "" && textCategorySelected == "") {
            textDecoration = ""
            activityDetailBinding.choice.visibility = View.GONE
        } else {
            activityDetailBinding.choice.visibility = View.VISIBLE
            if (textCountrySelected == "" || textCategorySelected == "") {
                textDecoration = ""
                activityDetailBinding.choice.text =
                    "Đã chọn: $textCategorySelected$textDecoration$textCountrySelected"
            } else {
                textDecoration = " • "
                activityDetailBinding.choice.text =
                    "Đã chọn: $textCategorySelected$textDecoration$textCountrySelected"
            }
        }
    }

    private val countries: ArrayList<String>
        get() {
            val list = ArrayList<String>()
            list.add("Mặc định")
            list.add("Japan")
            list.add("China")
            list.add("Việt Nam")
            return list
        }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_category -> {
                Log.d(TAG, "onClick: button_category")
                handleBottomSheetOption(OptionBottomSheetType.CATEGORY)
            }
            R.id.button_country -> {
                Log.d(TAG, "onClick: button_country")
                handleBottomSheetOption(OptionBottomSheetType.COUNTRY)
            }
        }
    }

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
    }
}