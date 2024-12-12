package com.bangkit.catloris.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catloris.databinding.ListItemArticleBinding
import com.bangkit.catloris.responses.ArticlesItem
import com.bangkit.catloris.utils.loadImage

class ArticleAdapter(private val context: Context, private var articleList: List<ArticlesItem>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val binding: ListItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.binding.articleImage.loadImage(article.urlToImage)
        holder.binding.articleTitle.text = article.title
        holder.binding.articleDescription.text = article.description

        holder.itemView.setOnClickListener {
            val url = article.url
            if(!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "URL not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(newList: List<ArticlesItem>) {
        (articleList as ArrayList).clear()
        (articleList as ArrayList).addAll(newList)
        notifyDataSetChanged()
    }

}