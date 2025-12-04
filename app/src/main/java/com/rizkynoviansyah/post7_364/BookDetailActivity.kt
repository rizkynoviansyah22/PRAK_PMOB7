// FILE: app/src/main/java/com/rizkynoviansyah/post7_364/BookDetailActivity.kt
package com.rizkynoviansyah.post7_364

import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.style.LeadingMarginSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rizkynoviansyah.post7_364.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar setup
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Book Detail"
        binding.toolbarDetail.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // read extras safely with defaults
        val title = intent.getStringExtra("title") ?: ""
        val originalTitle = intent.getStringExtra("originalTitle") ?: ""
        val releaseDate = intent.getStringExtra("releaseDate") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val pages = intent.getIntExtra("pages", 0)
        val cover = intent.getStringExtra("cover") ?: ""

        // set text views
        binding.tvTitleDetail.text = title
        binding.tvOriginalTitleDetail.text = originalTitle
        binding.tvOriginalTitleDetail.visibility = if (originalTitle.isEmpty()) View.GONE else View.VISIBLE
        binding.tvReleaseDateDetail.text = releaseDate
        binding.tvPages.text = "Pages: $pages"

        // load image: fitCenter + adjustViewBounds allows full image shown without crop
        Glide.with(this)
            .load(if (cover.isNotBlank()) cover else R.drawable.ic_book_placeholder)
            .placeholder(R.drawable.ic_book_placeholder)
            .error(R.drawable.ic_book_placeholder)
            .fitCenter()
            .into(binding.imgCoverBig)

        // apply indent and justification for description
        applyDescription(description)
    }

    private fun applyDescription(text: String) {
        if (text.isEmpty()) {
            binding.tvDescription.text = ""
            return
        }

        // First line indent 16dp
        val firstLineIndentPx = dpToPx(16)

        val spannable = SpannableStringBuilder(text)
        // apply LeadingMarginSpan to the first paragraph only
        val firstParaEnd = text.indexOf('\n').let { if (it == -1) text.length else it }
        spannable.setSpan(
            LeadingMarginSpan.Standard(firstLineIndentPx, 0),
            0,
            firstParaEnd,
            0
        )
        binding.tvDescription.text = spannable

        // justification for API >= 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvDescription.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
