package com.example.minerva.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.databinding.ItemNewsHomeBinding

class NewsAdapter(var newsList: MutableList<Article>, private val onSelect: (Article?) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    class NewsViewHolder(var view: ItemNewsHomeBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(binding)
    }

    fun changeData(newList: List<Article>) {
        newsList.clear()
        newsList.addAll(newList)
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]

        holder.view.titleHomeTextView.text = item.title
        holder.view.dateHomeTextView.text = item.publishedAt?.substringBeforeLast("T")
        holder.view.authorHomeTextview.text = item.author

        if (item.isFavourite)
            holder.view.addFavouriteHomeImageView.setImageResource(R.drawable.ic_added_favorite_24)
        else
            holder.view.addFavouriteHomeImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        holder.view.addFavouriteHomeImageView.setOnClickListener {
            onSelect(item)
            holder.view.addFavouriteHomeImageView.setImageResource(R.drawable.ic_added_favorite_24)
        }

        Glide.with(holder.view.HomeImageView.context)
            .load(item.urlToImage)
            .placeholder(R.drawable.testimage)
            .into(holder.view.HomeImageView)

    }

    override fun getItemCount(): Int = newsList.size

}