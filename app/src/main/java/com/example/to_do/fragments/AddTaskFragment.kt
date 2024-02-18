package com.example.to_do.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.core.widget.addTextChangedListener
import com.example.to_do.clearTime
import com.example.to_do.databinding.FragmentAddTaskBinding
import com.example.to_do.dp.TaskDM
import com.example.to_do.dp.TaskDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskFragment(private val onAddClick: () -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var selectDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClicks()
    }

    private fun handleClicks() {
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }
        binding.selectDateTv.setOnClickListener {
            showDatePicker()
        }
        binding.selectTimeTv.setOnClickListener {
            showTimePicker()
        }
        setTime()
    }

    private fun validate(): Boolean {
        var isValid = true
        val titleText = binding.titleTil.editText!!.text.toString()
        val descriptionText = binding.descriptionTil.editText!!.text.toString()
        if (titleText.isEmpty()) {
            binding.titleTil.error = "Please, Enter A Valid Title"
            isValid = false
        } else {
            binding.titleTil.error = null
        }

        if (descriptionText.isEmpty()) {
            binding.descriptionTil.error = "Please, Enter A Valid Description"
            isValid = false
        } else {
            binding.titleTil.editText!!.addTextChangedListener {
                binding.titleTil.error = null
            }
        }
        return isValid
    }

    private fun setTime() {
        binding.selectDateTv.text =
            "${selectDate.get(Calendar.DAY_OF_MONTH)} / ${selectDate.get(Calendar.MONTH) + 1} /${
                selectDate.get(Calendar.YEAR)
            }"
        binding.selectTimeTv.text =
            SimpleDateFormat("hh: mm a", Locale.getDefault()).format(Calendar.getInstance().time)

    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, day)
                binding.selectDateTv.text =
                    "${selectDate.get(Calendar.DAY_OF_MONTH)} / ${selectDate.get(Calendar.MONTH) + 1} /${
                        selectDate.get(Calendar.YEAR)
                    }"
            },
            selectDate.get(Calendar.YEAR),
            selectDate.get(Calendar.MONTH),
            selectDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _: TimePicker, hourOfDay: Int, minuteOfHour: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfHour)
                binding.selectTimeTv.text =
                    SimpleDateFormat("hh: mm a", Locale.getDefault()).format(calendar.time)

            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()

    }

    private fun addTask() {
        if (validate()) {
            val title = binding.titleTil.editText!!.text.toString()
            val description = binding.descriptionTil.editText!!.text.toString()
            val date = binding.selectDateTv.text.toString()
            val time = binding.selectTimeTv.text.toString()
            selectDate.clearTime()
            val task = TaskDM(
                title = title,
                description = description,
                time = time,
                dateAsString = date,
                date = selectDate.timeInMillis,
                isDone = false
            )
            TaskDatabase.getInstance(requireActivity().applicationContext).taskDao().insert(task)
            dismiss()
            onAddClick.invoke()
        }
    }
}