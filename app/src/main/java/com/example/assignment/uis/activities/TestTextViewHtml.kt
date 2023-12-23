package com.example.assignment.uis.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.core.text.HtmlCompat
import com.example.assignment.databinding.ActivityTestTextViewHtmlBinding
import com.example.assignment.utils.ImageGetter

class TestTextViewHtml : AppCompatActivity() {
    private val activityTestTextViewHtmlBinding: ActivityTestTextViewHtmlBinding by lazy {
        ActivityTestTextViewHtmlBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityTestTextViewHtmlBinding.root)

        activityTestTextViewHtmlBinding.displayHtml.setOnClickListener{
            if(activityTestTextViewHtmlBinding.editor.text.isNotEmpty()){
                displayHtml(activityTestTextViewHtmlBinding.editor.text.toString())
            }
        }
    }

    private fun displayHtml(html: String) {
        // Creating object of ImageGetter class you just created
        val imageGetter = ImageGetter(resources, activityTestTextViewHtmlBinding.htmlViewer)

        // Using Html framework to parse html
        val styledText= HtmlCompat.fromHtml(html,
            HtmlCompat.FROM_HTML_MODE_LEGACY,
            imageGetter,null)

        // to enable image/link clicking
        activityTestTextViewHtmlBinding.htmlViewer.movementMethod = LinkMovementMethod.getInstance()

        // setting the text after formatting html and downloading and setting images
        activityTestTextViewHtmlBinding.htmlViewer.text = styledText
    }
}