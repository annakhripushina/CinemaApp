package com.example.cinema_app.presentation.view.favourite

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema


class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val buttonDetail: TextView = itemView.findViewById(R.id.buttonDetail)
    private val buttonDelete: ImageView = itemView.findViewById(R.id.imageDelete)

    @SuppressLint("ResourceAsColor")
    fun bind(item: Cinema, listener: FavouriteAdapter.FavouriteClickListener) {
        titleView.text = item.title
        titleView.setTextColor(item.titleColor)

        Glide.with(imageView.context)
            .load(item.image)
            .centerCrop()
            .into(imageView)

        buttonDelete.setOnClickListener {
            listener.onDeleteClick(
                item,
                adapterPosition
            )
        }

        buttonDetail.setOnClickListener {
            listener.onCinemaClick(
                item,
                itemView,
                adapterPosition
            )
        }
    }
}