package com.example.to_do.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.to_do.adapter.TasksAdapter
import com.example.to_do.databinding.FragmentTasksBinding
import com.example.to_do.dp.TaskDM
import com.example.to_do.dp.TaskDatabase
import com.example.to_do.timeInMillis
import com.prolificinteractive.materialcalendarview.CalendarDay


class TasksFragment : Fragment(), TasksAdapter.OnDeleteClickListener,
    TasksAdapter.OnDoneClickListener {

    private var adapterTask = TasksAdapter(listOf())
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
        refreshTasksList()
        handleClicks()
    }


    private fun handleClicks() {
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            selectedDate = date
            showTasksListByDate()
        }
        adapterTask.setOnDeleteClickListener(this)
        adapterTask.setOnDoneClickListener(this)
    }

    fun refreshTasksList() {
        val tasksList =
            TaskDatabase.getInstance(requireActivity().applicationContext).taskDao().showAllTasks()
        adapterTask.updateTasksList(tasksList)
    }

    private fun showTasksListByDate() {
        val tasksList = TaskDatabase.getInstance(requireContext().applicationContext).taskDao()
            .getTasksByDate(selectedDate.timeInMillis())
        adapterTask.updateTasksList(tasksList)
    }

    override fun onDeleteClick(task: TaskDM) {

        val taskDao = TaskDatabase.getInstance(requireContext().applicationContext).taskDao()
        taskDao.delete(task)
        refreshTasksList()
    }

    override fun onDoneClick(task: TaskDM) {
        task.isDone = true
        TaskDatabase.getInstance(requireContext().applicationContext).taskDao().update(task)
        refreshTasksList()
    }
}
