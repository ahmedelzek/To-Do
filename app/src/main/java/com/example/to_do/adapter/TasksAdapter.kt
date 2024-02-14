package com.example.to_do.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.to_do.R
import com.example.to_do.databinding.ItemTaskBinding
import com.example.to_do.dp.TaskDM

class TasksAdapter(private var taskList: List<TaskDM>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    fun updateTasksList(newTasksList: List<TaskDM>) {
        taskList = newTasksList
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        ViewHolder(binding.root) {
        fun bind(task: TaskDM) {

            binding.apply {
                title.text = task.title
                time.text = task.time
            }
            onDeleteClickListener?.let { onDeleteClickListener ->
                binding.deleteBtn.setOnClickListener {
                    onDeleteClickListener.onDeleteClick(task)
                }
            }
            onDoneClickListener?.let { onDoneClickListener ->
                binding.btnTaskIsDone.setOnClickListener {
                    onDoneClickListener.onDoneClick(task)
                }
            }


            if (task.isDone!!) {
                binding.title.setTextColor(Color.GREEN)
                binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
                binding.btnTaskIsDone.setImageResource(R.drawable.done)
            }
        }
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(task: TaskDM)
    }

    private var onDeleteClickListener: OnDeleteClickListener? = null
    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    interface OnDoneClickListener {
        fun onDoneClick(task: TaskDM)
    }

    private var onDoneClickListener: OnDoneClickListener? = null
    fun setOnDoneClickListener(listener: OnDoneClickListener) {
        onDoneClickListener = listener
    }

}