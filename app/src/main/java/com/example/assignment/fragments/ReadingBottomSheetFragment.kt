package com.example.assignment.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.example.assignment.R
import com.example.assignment.adapters.AdapterSpinner
import com.example.assignment.databinding.BottomSheetReadingLayoutBinding
import com.example.assignment.models.Chapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReadingBottomSheetFragment : BottomSheetDialogFragment() {
    private var bottomSheetReadingLayoutBinding: BottomSheetReadingLayoutBinding? = null
    private var listChapter: ArrayList<Chapter>? = null
    private var initChapterSelected = 0
    private var initFontSize = 0f
    private val minFontSize = 14
    private val maxFontSize = 22
    private val totalFontSize = maxFontSize - minFontSize
    private var adapterSpinner: AdapterSpinner? = null
    private var handlerFontSizeChangeListener: HandlerFontSizeChangeListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        val data = arguments
        if (data != null) {
            listChapter = data.getSerializable("LIST_CHAPTER") as ArrayList<Chapter>?
            initFontSize = data.getFloat("INIT_FONT_SIZE")
            initChapterSelected = data.getInt("INIT_CHAPTER_SELECTED")
        }
    }

    fun setHandlerFontSizeChangeListener(handlerFontSizeChangeListener: HandlerFontSizeChangeListener?): ReadingBottomSheetFragment {
        this.handlerFontSizeChangeListener = handlerFontSizeChangeListener
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetReadingLayoutBinding =
            BottomSheetReadingLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(bottomSheetReadingLayoutBinding!!.root)
        init()
        listenerEvent()
        return bottomSheetDialog
    }

    fun init() {
        adapterSpinner =
            AdapterSpinner(requireContext(), R.layout.item_chapter_layout, listChapter!!)
        bottomSheetReadingLayoutBinding!!.chapterSpinner.adapter = adapterSpinner
        bottomSheetReadingLayoutBinding!!.chapterSpinner.setSelection(initChapterSelected)
        bottomSheetReadingLayoutBinding!!.fluidSlider.position =
            (initFontSize - minFontSize) / totalFontSize
        bottomSheetReadingLayoutBinding!!.fluidSlider.bubbleText = initFontSize.toInt().toString()
        bottomSheetReadingLayoutBinding!!.fluidSlider.startText = minFontSize.toString()
        bottomSheetReadingLayoutBinding!!.fluidSlider.endText = maxFontSize.toString()
    }

    private fun listenerEvent() {
        bottomSheetReadingLayoutBinding!!.chapterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    handlerFontSizeChangeListener!!.onChangeChapter(
                        i,
                        false,
                        this@ReadingBottomSheetFragment
                    )
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    handlerFontSizeChangeListener!!.onChangeChapter(
                        initChapterSelected,
                        true,
                        this@ReadingBottomSheetFragment
                    )
                }
            }
        bottomSheetReadingLayoutBinding!!.fluidSlider.beginTrackingListener = {
            bottomSheetReadingLayoutBinding!!.titleSlider.setTextColor(resources.getColor(android.R.color.transparent))
            Unit
        }
        bottomSheetReadingLayoutBinding!!.fluidSlider.endTrackingListener = {
            bottomSheetReadingLayoutBinding!!.titleSlider.setTextColor(resources.getColor(R.color.white))
            Unit
        }
        bottomSheetReadingLayoutBinding!!.fluidSlider.positionListener = { pos: Float ->
            Log.d(TAG, "invoke: $pos")
            val value = (minFontSize + totalFontSize * pos).toInt().toString()
            bottomSheetReadingLayoutBinding!!.fluidSlider.bubbleText = value
            //handle callback
            handlerFontSizeChangeListener!!.onChange(
                (minFontSize + totalFontSize * pos).toInt().toFloat()
            )
            Unit
        }
    }

    interface HandlerFontSizeChangeListener {
        fun onChange(fontSize: Float)
        fun onChangeChapter(
            newChapterIndex: Int,
            isNothing: Boolean,
            readingBottomSheetFragment: ReadingBottomSheetFragment?
        )
    }

    companion object {
        private val TAG = ReadingBottomSheetFragment::class.java.simpleName
        fun newInstance(
            listChapter: ArrayList<Chapter>,
            initChapterSelected: Int,
            initFontSize: Float
        ): ReadingBottomSheetFragment {
            val readingBottomSheetFragment = ReadingBottomSheetFragment()
            val bundle = Bundle()
            bundle.putSerializable("LIST_CHAPTER", listChapter)
            bundle.putInt("INIT_CHAPTER_SELECTED", initChapterSelected)
            bundle.putFloat("INIT_FONT_SIZE", initFontSize)
            readingBottomSheetFragment.arguments = bundle
            return readingBottomSheetFragment
        }
    }
}