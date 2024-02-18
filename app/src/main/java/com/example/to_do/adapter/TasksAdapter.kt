package com.example.to_do.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
                date.text = task.dateAsString
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
            onTaskClickListener?.let { onTaskClickListener ->
                binding.taskCardView.setOnClickListener {
                    onTaskClickListener.onItemClick(task)
                }

            }

            if (task.isDone!!) {
                binding.apply {
                    title.setTextColor(Color.GREEN)
                    draggingBar.setImageResource(R.drawable.dragging_bar_done)
                    btnTaskIsDone.setImageResource(R.drawable.done)
                }

            } else {
                binding.apply {
                    title.setTextColor(ContextCompat.getColor(title.context, R.color.blue))
                    draggingBar.setImageResource(R.drawable.dragging_bar)
                    btnTaskIsDone.setImageResource(R.drawable.check_mark)
                }
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

    interface OnTaskClickListener {
        fun onItemClick(task: TaskDM)
    }

    private var onTaskClickListener: OnTaskClickListener? = null
    fun setOnItemClickListener(listener: OnTaskClickListener) {
        onTaskClickListener = listener
    }

}