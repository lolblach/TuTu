package com.lolblach333.tutu.presentation

import android.graphics.ColorSpace.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lolblach333.tutu.R
import com.lolblach333.tutu.data.news.Article

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Article, RecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(news)
        }
        holder.bind(news)
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageview)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)
        private val authorView: TextView = itemView.findViewById(R.id.author)
        private val publishedAtView: TextView = itemView.findViewById(R.id.publishedAt)

        fun bind(item: Article) {
            Glide.with(imageView).load(item.image).placeholder(R.drawable.no_result).into(imageView)
            titleView.text = item.title
            descriptionView.text = item.description
            authorView.text = item.author
            publishedAtView.text = item.publishedAt
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.image == newItem.image
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
