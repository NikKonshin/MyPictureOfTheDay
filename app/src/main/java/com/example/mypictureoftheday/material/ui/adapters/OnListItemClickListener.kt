package com.example.mypictureoftheday.material.ui.adapters

import com.example.mypictureoftheday.model.ToDoData

interface OnListItemClickListener {
    fun onItemClick(data: ToDoData)
}