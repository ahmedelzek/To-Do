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
    private lateinit var binding: ActivityHomeBinding
    private var tasksFragment = TasksFragment()
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
                    loadFragment(tasksFragment)
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
            loadFragment(tasksFragment)
    }

    private fun showAddTaskFragment() {
        val bottomSheetFragment = AddTaskFragment {
            tasksFragment.refreshTasksList()
        }
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}