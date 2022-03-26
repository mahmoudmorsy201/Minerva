package com.example.minerva.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.databinding.ItemNewsHomeBinding


class FavouriteArticlesAdapter(
    var newsList: MutableList<Article>,
    private val onSelect: (Article?) -> Unit
) : RecyclerView.Adapter<FavouriteArticlesAdapter.FavouriteViewHolder>() {


    class FavouriteViewHolder(var view: ItemNewsHomeBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding =
            ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavouriteViewHolder(binding)
    }

    fun changeData(newList: List<Article>) {
        newsList.clear()
        newsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val item = newsList[position]

        holder.view.titleHomeTextView.text = item.title
        holder.view.dateHomeTextView.text = item.publishedAt?.substringBeforeLast("T")
        holder.view.authorHomeTextview.text = item.author
        holder.view.addFavouriteHomeImageView.setOnClickListener {
            onSelect(item)
        }
        if (item.isFavourite)
            holder.view.addFavouriteHomeImageView.setImageResource(R.drawable.ic_added_favorite_24)
        Glide.with(holder.view.HomeImageView.context)
            .load(item.urlToImage)
            .into(holder.view.HomeImageView)

    }

    override fun getItemCount(): Int = newsList.size


}