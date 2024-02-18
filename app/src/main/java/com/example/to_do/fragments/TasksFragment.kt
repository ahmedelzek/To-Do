package com.example.to_do.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.to_do.Constant
import com.example.to_do.activities.TaskDetailsActivity
import com.example.to_do.adapter.TasksAdapter
import com.example.to_do.databinding.FragmentTasksBinding
import com.example.to_do.dp.TaskDM
import com.example.to_do.dp.TaskDatabase
import com.example.to_do.timeInMillis
import com.prolificinteractive.materialcalendarview.CalendarDay


class TasksFragment : Fragment(), TasksAdapter.OnDeleteClickListener,
    TasksAdapter.OnDoneClickListener, TasksAdapter.OnTaskClickListener {

    private var adapterTask = TasksAdapter(listOf())
    private lateinit var tasksList: List<TaskDM>
    private lateinit var binding: FragmentTasksBinding
    private lateinit var selectedDate: CalendarDay
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTasks.adapter = adapterTask
        handleClicks()
    }

    override fun onResume() {
        super.onResume()
        refreshTasksList()
    }

    private fun handleClicks() {
        refreshTasksList()
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            selectedDate = date
            loadTasksByDate()
        }
        adapterTask.setOnDeleteClickListener(this)
        adapterTask.setOnDoneClickListener(this)
        adapterTask.setOnItemClickListener(this)
    }

    fun refreshTasksList() {
        tasksList =
            TaskDatabase.getInstance(requireActivity().applicationContext).taskDao().showAllTasks()
        adapterTask.updateTasksList(tasksList)
    }

    private fun loadTasksByDate() {
        val tasksList = TaskDatabase.getInstance(requireContext().applicationContext).taskDao()
            .getTasksByDate(selectedDate.timeInMillis())
        adapterTask.updateTasksList(tasksList)
    }


    override fun onDeleteClick(task: TaskDM) {

        val taskDao = TaskDatabase.getInstance(requireContext().applicationContext).taskDao()
        taskDao.delete(task)
        binding.recyclerViewTasks.adapter = adapterTask
        refreshTasksList()
        Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onDoneClick(task: TaskDM) {
        task.isDone = task.isDone != true
        TaskDatabase.getInstance(requireContext().applicationContext).taskDao().update(task)
        refreshTasksList()
    }

    override fun onItemClick(task: TaskDM) {
        val intent = Intent(requireContext(), TaskDetailsActivity::class.java)
        intent.putExtra(Constant.TASK, task)
        startActivity(intent)
    }
}
