package com.example.to_do.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.to_do.Constant
import com.example.to_do.clearTime
import com.example.to_do.databinding.ActivityTaskDetailsBinding
import com.example.to_do.dp.TaskDM
import com.example.to_do.dp.TaskDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailsBinding
    private var selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicks()
    }

    private fun initClicks() {
        setInitialData()
        binding.content.selectDateTv.setOnClickListener {
            showDatePickerToEdit()
        }
        binding.content.selectTimeTv.setOnClickListener {
            showTimePickerToEdit()
        }
        binding.content.btnSave.setOnClickListener {
            saveEdits()
        }
    }

    private fun setInitialData() {
        val task = intent.getSerializableExtra(Constant.TASK) as? TaskDM
        binding.content.titleTil.editText?.setText(task?.title)
        binding.content.descriptionTil.editText?.setText(task?.description)
        binding.content.selectDateTv.text = task?.dateAsString
        binding.content.selectTimeTv.text = task?.time
        selectedDate.timeInMillis = task?.date!!
    }

    private fun validate(): Boolean {
        var isValid = true
        val titleText = binding.content.titleTil.editText!!.text.toString()
        val descriptionText = binding.content.descriptionTil.editText!!.text.toString()
        if (titleText.isEmpty()) {
            binding.content.titleTil.error = "Please, Enter A Valid Title"
            isValid = false
        } else {
            binding.content.titleTil.error = null
        }

        if (descriptionText.isEmpty()) {
            binding.content.descriptionTil.error = "Please, Enter A Valid Description"
            isValid = false
        } else {
            binding.content.titleTil.editText!!.addTextChangedListener {
                binding.content.titleTil.error = null
            }
        }
        return isValid
    }

    private fun saveEdits() {
        if (validate()) {
            val task = intent.getSerializableExtra(Constant.TASK) as? TaskDM
            task?.title = binding.content.titleTil.editText?.text.toString()
            task?.description = binding.content.descriptionTil.editText?.text.toString()
            task?.dateAsString = binding.content.selectDateTv.text.toString()
            selectedDate.clearTime()
            task?.date = selectedDate.timeInMillis
            task?.isDone = task?.isDone
            TaskDatabase.getInstance(this).taskDao().update(task!!)
            finish()
        }

    }


    private fun showDatePickerToEdit() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, day)
                binding.content.selectDateTv.text =
                    "${selectedDate.get(Calendar.DAY_OF_MONTH)} / ${selectedDate.get(Calendar.MONTH) + 1} /${
                        selectedDate.get(Calendar.YEAR)
                    }"
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showTimePickerToEdit() {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, hourOfDay: Int, minuteOfHour: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfHour)
                binding.content.selectTimeTv.text =
                    SimpleDateFormat("hh: mm a", Locale.getDefault()).format(calendar.time)

            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()

    }

}