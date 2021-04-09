package com.example.mypictureoftheday.material.ui.adapters

import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypictureoftheday.R
import com.example.mypictureoftheday.material.ui.adapters.holders.BaseViewHolder
import com.example.mypictureoftheday.model.ToDoData
import kotlinx.android.synthetic.main.item_rv_to_do_list_fragment.view.*

class ToDoListRVAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<ToDoData>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rv_to_do_list_fragment, parent, false)
        )


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = data.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = ToDoData("Введите название", "Введите задачу", false, false)


    inner class ViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(dataItem: ToDoData) {

            itemView.tv_title_item_rv_to_do_list.text = data[layoutPosition].title
            itemView.tv_task_item_rv_to_do_list.text = data[layoutPosition].task

            itemView.iv_check_out_item_rv_to_do_list.visibility =
                if (data[layoutPosition].isComplete) View.GONE else View.VISIBLE
            itemView.iv_check_completed_item_rv_to_do_list.visibility =
                if (data[layoutPosition].isComplete) View.VISIBLE else View.GONE

            itemView.tv_task_item_rv_to_do_list.visibility =
                if (data[layoutPosition].isOpenETTask) View.GONE else View.VISIBLE
            itemView.et_task_item_rv_to_do_list.visibility =
                if (data[layoutPosition].isOpenETTask) View.VISIBLE else View.GONE
            itemView.iv_save_task_item_rv_to_do_list.visibility =
                if (data[layoutPosition].isOpenETTask) View.VISIBLE else View.GONE

            itemView.iv_archive_item_rv_to_do_list.setOnClickListener {
                data[layoutPosition].isOpenETTask = false
                itemView.motion_container2.setTransition(R.id.start2, R.id.end2)
                itemView.motion_container2.transitionToEnd()
            }

            itemView.tv_title_item_rv_to_do_list.setOnClickListener {
                itemView.container_item_to_do.setTransition(R.id.start1, R.id.end1);
                itemView.container_item_to_do.transitionToEnd()
            }

            itemView.iv_check_out_item_rv_to_do_list.setOnClickListener {
                data[layoutPosition].isComplete = true
                notifyItemChanged(layoutPosition)
            }

            itemView.iv_check_completed_item_rv_to_do_list.setOnClickListener {
                data[layoutPosition].isComplete = false
                notifyItemChanged(layoutPosition)
            }

            itemView.iv_save_title_item_rv_to_do_list.setOnClickListener {
                data[layoutPosition].title = itemView.et_title_item_rv_to_do_list.text.toString()
                itemView.container_item_to_do.setTransition(R.id.end1, R.id.start1);
                itemView.container_item_to_do.transitionToEnd()
                itemView.et_title_item_rv_to_do_list.text =
                    Editable.Factory.getInstance().newEditable(data[layoutPosition].task)
                notifyItemChanged(layoutPosition)
            }

            itemView.tv_task_item_rv_to_do_list.setOnClickListener {
                data[layoutPosition].isOpenETTask = true
                notifyItemChanged(layoutPosition)
            }

            itemView.iv_save_task_item_rv_to_do_list.setOnClickListener {
                itemView.motion_container2.setTransition(R.id.end2, R.id.start2)
                itemView.motion_container2.transitionToEnd()
                if (itemView.et_task_item_rv_to_do_list.text.toString() != "") {
                    data[layoutPosition].task = itemView.et_task_item_rv_to_do_list.text.toString()
                }
                itemView.et_task_item_rv_to_do_list.text =
                    Editable.Factory.getInstance().newEditable("")
                data[layoutPosition].isOpenETTask = false
                notifyItemChanged(layoutPosition)
            }

            itemView.iv_delete_item_rv_to_do_list.setOnClickListener { removeItem() }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(itemView.context.getColor(R.color.bottom_sheet_color_background))
        }
    }
}