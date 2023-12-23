package com.example.assignment.uis.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.assignment.R
import com.example.assignment.databinding.ActivityDetailComicBinding
import com.example.assignment.models.Comic
import com.example.assignment.utils.AppAnimation

class DetailComicActivity : AppCompatActivity(), View.OnClickListener {
    private val binding: ActivityDetailComicBinding by lazy {
        ActivityDetailComicBinding.inflate(layoutInflater)
    }
    private var isExpandedSynopsisLayout = false
    private var currentComic: Comic? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        addListenerEvent()
    }

    fun init() {
        currentComic = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("comic_model", Comic::class.java)
        } else {
            intent.getSerializableExtra("comic_model") as Comic?
        }

        if (currentComic != null) {
            binding.categoryComic.text = currentComic!!.categories.trim()
            binding.textAuthor.text = currentComic!!.author.trim()
            binding.textCountry.text = currentComic!!.country.trim()
            binding.textSynopsis.text = currentComic!!.synopsis.trim()
            binding.textSubTitle.text = currentComic!!.subTitle.trim()
            binding.titleComic.text = currentComic!!.title.trim()
            binding.publishBy.text = currentComic!!.publishBy.trim()

            Glide.with(this@DetailComicActivity).load(currentComic!!.thumbnail).override(200, 230)
                .placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.thumbnailComic)
        }
    }

    private fun addListenerEvent() {
        binding.expandSynopsisButton.setOnClickListener(this@DetailComicActivity)
        binding.readButton.setOnClickListener(this@DetailComicActivity)
        binding.favoriteButton.setOnClickListener(this@DetailComicActivity)
    }

    private fun handleExpandSynopsis() {
        if (isExpandedSynopsisLayout) {
            AppAnimation.collapse(
                binding.expandSynopsisLayout,
                150,
                binding.maskSynopsis
            )
            AppAnimation.rotate(binding.expandSynopsisButton, 0f, 500)
        } else {
            AppAnimation.expand(
                binding.expandSynopsisLayout,
                binding.maskSynopsis
            )
            AppAnimation.rotate(binding.expandSynopsisButton, 180f, 500)
        }
        isExpandedSynopsisLayout = !isExpandedSynopsisLayout
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.expand_synopsis_button -> handleExpandSynopsis()
            R.id.read_button -> {
                val dataIntent: Intent = Intent(this@DetailComicActivity, ReadingActivity::class.java)
                dataIntent.putExtra("comic_id", currentComic?.id);
                startActivity(dataIntent)
            }
            R.id.favorite_button -> Log.d(TAG, "onClick: favorite_button")
        }
    }

    companion object {
        private val TAG = DetailComicActivity::class.java.simpleName
    }
}