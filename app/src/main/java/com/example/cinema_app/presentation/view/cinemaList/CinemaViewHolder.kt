package com.example.cinema_app.presentation.view.cinemaList

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.R


class CinemaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.findViewById(R.id.titleView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val buttonDetail: TextView = itemView.findViewById(R.id.buttonDetail)

    @SuppressLint("ResourceAsColor")
    fun bind(item: Cinema, listener: CinemaAdapter.CinemaClickListener) {
        titleView.setText(item.title)
        //imageView.setImageURI(item.image)
        titleView.setTextColor(item.titleColor)

        Glide.with(imageView.context)
            .load(item.image)
            //.placeholder(R.drawable.ic_image)
            //.error(R.drawable.ic_error)
            //.override(image2.resources.getDimensionPixelSize(R.dimen.image_size))
            .centerCrop()
            .into(imageView)

        buttonDetail.setOnClickListener {
            listener.onCinemaClick(
                item,
                itemView,
                adapterPosition
            )
        }
        itemView.setOnLongClickListener {
            listener.onLongCinemaClick(
                item,
                itemView,
                adapterPosition
            )
            return@setOnLongClickListener true
        }
    }

}