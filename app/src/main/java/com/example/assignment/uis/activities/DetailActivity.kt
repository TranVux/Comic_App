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
                if (type == OptionBottomSheetType.COUNTRY) countries else dataCategory,
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

    @get:SuppressLint("ResourceType")
    val dataCategory: ArrayList<Category>
        get() {
            val listData = ArrayList<Category>()
            listData.add(
                Category(
                    "55444546",
                    "Mặc định",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_blue_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Hành động",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_blue_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trinh thám",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Lạng mãn",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Học tập",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green_light)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Thể thao",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_yellow)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trường học",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_yellow_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trẻ em",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_blue_sky)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Tưởng tượng",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_pink_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Kinh dị",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_pink_light)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Phép thuật",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_red_light)
                )
            )
            return listData
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