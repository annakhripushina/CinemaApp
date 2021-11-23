package com.example.cinema_app

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MyItemDecorator() : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(20, 20, 20, 20)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            drawItemView(child, c)
        }
    }

    private fun drawItemView(child: View, c: Canvas) {
        val drawable = ContextCompat.getDrawable(child.context, R.drawable.chat_message_bg)
        val viewColor = ContextCompat.getColor(child.context, R.color.white)
        val right = child.right
        val left = child.left
        val bottom = child.bottom
        val top = child.top

        drawable?.setTint(viewColor)
        drawable?.setBounds(left, top, right, bottom)
        drawable?.draw(c)
    }

}