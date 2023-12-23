package com.example.assignment.uis.activities

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.components.HeaderApp.HeaderButtonHandler
import com.example.assignment.databinding.ActivityReadingBinding
import com.example.assignment.uis.fragments.ReadingBottomSheetFragment
import com.example.assignment.uis.fragments.ReadingBottomSheetFragment.HandlerFontSizeChangeListener
import com.example.assignment.models.Chapter
import com.example.assignment.models.Comic
import com.example.assignment.utils.Convert
import com.example.assignment.utils.ImageGetter
import com.example.assignment.viewmodels.ComicViewModel

class ReadingActivity : AppCompatActivity() {
    private val binding: ActivityReadingBinding by lazy {
        ActivityReadingBinding.inflate(layoutInflater)
    }
    private var textSize = 0f
    private var currentChapterSelectedIndex = 0
    private var currentChapter: Chapter? = null
    private var currentComic: Comic? = null;
    private var listChapter: ArrayList<Chapter>? = null;

    //comic viewModel
    private val comicViewModel: ComicViewModel by lazy {
        ViewModelProvider(
            this,
            ComicViewModel.ComicViewModelFactory(application)
        )[ComicViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        addEventListener()
    }

    fun init() {
        handleGetDetailComic();
        Convert.pixelsToSp(this@ReadingActivity, binding.textContent.textSize)
    }

    private fun handleGetDetailComic() {
        val comicId: String = intent.getStringExtra("comic_id") ?: "";
        Log.d(TAG, "handleGetDetailComic: $comicId")
        comicViewModel.getDetailComic(comicId, onSuccess = { res ->
            listChapter = res.result.chapters as ArrayList<Chapter>;
            currentComic = res.result.comic;

            currentChapterSelectedIndex = 0
            currentChapter = listChapter!![currentChapterSelectedIndex]

            binding.headerApp.setTitle(currentComic!!.title)
            handleChangeContent(currentChapter!!)
        }, onError = { error ->
            Log.e(TAG, "handleGetDetailComic: $error")
        })
    }

    private fun addEventListener() {
        binding.headerApp.setHeaderButtonHandler(object : HeaderButtonHandler {
            override fun onLeftButtonClick() {}
            override fun onRightButtonClick() {}
            override fun onUserContainerClick() {}
            override fun onCenterContainerClick() {
                val readingBottomSheetFragment = ReadingBottomSheetFragment.newInstance(
                    listChapter!!, currentChapterSelectedIndex, textSize
                )
                    .setHandlerFontSizeChangeListener(object : HandlerFontSizeChangeListener {
                        override fun onChange(fontSize: Float) {
                            binding.textContent.textSize = fontSize
                            textSize = fontSize
                        }

                        override fun onChangeChapter(
                            newChapterIndex: Int,
                            isNothing: Boolean,
                            readingBottomSheetFragment: ReadingBottomSheetFragment?
                        ) {
                            if (!isNothing) {
                                currentChapter = listChapter?.get(newChapterIndex)
                                currentChapter?.let { handleChangeContent(it) }
                                binding.scrollview.scrollTo(0, 0)

                                if (currentChapterSelectedIndex != newChapterIndex) {
                                    currentChapterSelectedIndex = newChapterIndex
                                    readingBottomSheetFragment!!.dismiss()
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

    private fun handleChangeContent(currentChapter: Chapter) {
        if (currentChapter.hasHtml == 1) {
            displayHtml(currentChapter.content)
        } else {
            binding.textContent.text = currentChapter.content
        }
        binding.headerApp.setSubTitle(currentChapter.title)
    }

    private fun displayHtml(html: String) {
        // Creating object of ImageGetter class you just created
        val imageGetter = ImageGetter(resources, binding.textContent)

        // Using Html framework to parse html
        val styledText = HtmlCompat.fromHtml(
            html,
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            imageGetter, null
        )

        // to enable image/link clicking
        binding.textContent.movementMethod = LinkMovementMethod.getInstance()

        // setting the text after formatting html and downloading and setting images
        binding.textContent.text = styledText
    }

    companion object {
        private val TAG = ReadingActivity::class.java.simpleName
    }
}