package com.example.mypictureoftheday.material.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.adapters.ItemTouchHelperCallback
import com.example.mypictureoftheday.material.ui.adapters.OnListItemClickListener
import com.example.mypictureoftheday.material.ui.adapters.OnStartDragListener
import com.example.mypictureoftheday.material.ui.adapters.ToDoListRVAdapter
import com.example.mypictureoftheday.model.ToDoData
import kotlinx.android.synthetic.main.fragment_to_do_list.*

class ToDoListFragment : Fragment() {

    private lateinit var adapter: ToDoListRVAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arrayListOf<ToDoData>(generateItem())

        adapter = ToDoListRVAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: ToDoData) {

                }
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        rv_to_do_list_fragment.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(rv_to_do_list_fragment)
    }

    private fun generateItem() = ToDoData("Введите название", "Введите задачу", false)
}