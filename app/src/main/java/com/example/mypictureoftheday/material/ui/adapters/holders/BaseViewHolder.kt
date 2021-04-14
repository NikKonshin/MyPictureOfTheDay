package com.example.mypictureoftheday.material.ui.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mypictureoftheday.model.ToDoData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: ToDoData)
}