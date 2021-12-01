package com.example.cinema_app.presentation.view.favourite

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.R


class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val buttonDetail: TextView = itemView.findViewById(R.id.buttonDetail)
    private val buttonDelete: TextView = itemView.findViewById(R.id.buttonDelete)

    @SuppressLint("ResourceAsColor")
    fun bind(item: Cinema, listener: FavouriteAdapter.FavouriteClickListener) {
        titleView.setText(item.title)
        imageView.setImageResource(item.image)
        titleView.setTextColor(item.titleColor)

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