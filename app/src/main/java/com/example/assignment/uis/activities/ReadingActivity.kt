package com.example.assignment.uis.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.components.HeaderApp.HeaderButtonHandler
import com.example.assignment.databinding.ActivityReadingBinding
import com.example.assignment.uis.fragments.ReadingBottomSheetFragment
import com.example.assignment.uis.fragments.ReadingBottomSheetFragment.HandlerFontSizeChangeListener
import com.example.assignment.models.Chapter
import com.example.assignment.utils.Convert

class ReadingActivity : AppCompatActivity() {
    private val activityReadingBinding: ActivityReadingBinding by lazy {
        ActivityReadingBinding.inflate(layoutInflater)
    }
    private var textSize = 0f
    private var currentChapterSelectedIndex = 0
    private var currentChapter: Chapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityReadingBinding.root)
        init()
        addEventListener()
    }

    fun init() {
        textSize =
            Convert.pixelsToSp(this@ReadingActivity, activityReadingBinding.textContent.textSize)
        currentChapterSelectedIndex = 0
        currentChapter = data[currentChapterSelectedIndex]
    }

    private fun addEventListener() {
        activityReadingBinding.headerApp.setHeaderButtonHandler(object : HeaderButtonHandler {
            override fun onLeftButtonClick() {}
            override fun onRightButtonClick() {}
            override fun onUserContainerClick() {}
            override fun onCenterContainerClick() {
                val readingBottomSheetFragment = ReadingBottomSheetFragment.newInstance(
                    data, currentChapterSelectedIndex, textSize
                )
                    .setHandlerFontSizeChangeListener(object : HandlerFontSizeChangeListener {
                        override fun onChange(_textSize: Float) {
                            activityReadingBinding.textContent.textSize = _textSize
                            textSize = _textSize
                        }

                        override fun onChangeChapter(
                            newChapterIndex: Int,
                            isNothing: Boolean,
                            readingBottomSheetFragment: ReadingBottomSheetFragment?
                        ) {
                            if (!isNothing) {
                                currentChapter = data.get(newChapterIndex)
                                if (currentChapterSelectedIndex != newChapterIndex) {
                                    readingBottomSheetFragment!!.dismiss()
                                    currentChapterSelectedIndex = newChapterIndex
                                }
                                Log.d(TAG, "onChangeChapter: " + currentChapter!!.title)
                            }
                        }
                    })
                readingBottomSheetFragment.show(
                    supportFragmentManager,
                    readingBottomSheetFragment.tag
                )
            }
        })
    }

    val data: ArrayList<Chapter>
        get() {
            val list = ArrayList<Chapter>()
            list.apply {
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 1",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        0,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 2",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        1,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 3",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        2,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 4",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        3,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 5",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        4,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 6",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        5,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 7",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        6,
                        "234234"
                    )
                )
                add(
                    Chapter(
                        "àasfsaf",
                        "Chương 8",
                        "kashkfhaskhfkhasfhkashkfhkashfkhasdf",
                        7,
                        "234234"
                    )
                )
            }
            return list
        }

    companion object {
        private val TAG = ReadingActivity::class.java.simpleName
    }
}