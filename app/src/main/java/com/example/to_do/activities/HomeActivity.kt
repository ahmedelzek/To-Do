package com.example.to_do.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.to_do.R
import com.example.to_do.databinding.ActivityHomeBinding
import com.example.to_do.fragments.AddTaskFragment
import com.example.to_do.fragments.SettingsFragment
import com.example.to_do.fragments.TasksFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleClicks()
        loadTasksFragment(savedInstanceState)
    }

    private fun handleClicks() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tasks -> {
                    loadFragment(TasksFragment())
                    true
                }

                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }

                else -> false
            }


        }
        binding.fabAddTask.setOnClickListener {
            showAddTaskFragment()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun loadTasksFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null)
            loadFragment(TasksFragment())
    }

    private fun showAddTaskFragment() {
        val bottomSheetFragment = AddTaskFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}