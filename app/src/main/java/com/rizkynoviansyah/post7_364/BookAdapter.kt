package com.rizkynoviansyah.post7_364

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkynoviansyah.post7_364.databinding.ItemBookBinding
import com.rizkynoviansyah.post7_364.Book



class BookAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<BookAdapter.VH>() {

    inner class VH(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvTitle.text = book.title
            binding.tvOriginalTitle.text = book.originalTitle ?: ""
            binding.tvOriginalTitle.visibility =
                if (book.originalTitle.isNullOrEmpty()) View.GONE else View.VISIBLE

            binding.tvReleaseDate.text = book.releaseDate ?: ""

            Glide.with(binding.root.context)
                .load(book.cover) // gunakan field 'cover' dari model kita
                .placeholder(R.drawable.ic_book_placeholder)
                .error(R.drawable.ic_book_placeholder)
                .into(binding.imgCover)

            binding.root.setOnClickListener {
                val ctx = binding.root.context
                val i = Intent(ctx, BookDetailActivity::class.java).apply {
                    putExtra("title", book.title)
                    putExtra("originalTitle", book.originalTitle)
                    putExtra("releaseDate", book.releaseDate)
                    putExtra("description", book.description)
                    putExtra("pages", book.pages )
                    putExtra("cover", book.cover)
                }
                ctx.startActivity(i)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(books[position])

    override fun getItemCount(): Int = books.size
}
