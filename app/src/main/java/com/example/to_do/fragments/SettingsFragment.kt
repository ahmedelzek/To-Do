package com.example.to_do.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.to_do.Constant
import com.example.to_do.R
import com.example.to_do.databinding.FragmentSettingsBinding
import java.util.Locale


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectLanguage()
        selectTheme()
    }

    override fun onResume() {
        super.onResume()
        selectTheme()
    }

    private fun selectLanguage() {
        val languages = resources.getStringArray(R.array.languages)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, languages)
        binding.autoCompleteTVLanguages.setAdapter(adapter)
        binding.autoCompleteTVLanguages.setOnItemClickListener { _, _, position, _ ->
            val selectItem = adapter.getItem(position)
            when (position) {
                0 -> {
                    setLocale(Constant.ENGLISH_CODE)
                    binding.autoCompleteTVLanguages.setText(selectItem.toString())
                }

                1 -> {
                    setLocale(Constant.ARABIC_CODE)
                    binding.autoCompleteTVLanguages.setText(selectItem.toString())
                }
            }
        }
    }

    private fun selectTheme() {
        val themes = resources.getStringArray(R.array.modes)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, themes)
        binding.autoCompleteTVModes.setAdapter(adapter)
        binding.autoCompleteTVModes.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    toggleThemeForLight(this)
                    binding.autoCompleteTVModes.setText(R.string.light)
                    binding.modeTil.setStartIconDrawable(R.drawable.ic_light_mode)
                }

                1 -> {
                    toggleThemeForNight(this)
                    binding.autoCompleteTVModes.setText(R.string.dark)
                    binding.modeTil.setStartIconDrawable(R.drawable.ic_dark)
                }
            }
        }
    }

    private fun toggleThemeForLight(fragment: Fragment) {
        val currentLightMode =
            fragment.requireActivity().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newLightMode = if (currentLightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(newLightMode)
    }

    private fun toggleThemeForNight(fragment: Fragment) {
        val currentLightMode =
            fragment.requireActivity().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newLightMode = if (currentLightMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(newLightMode)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        activity?.let {
            @Suppress("DEPRECATION")
            requireActivity().baseContext.resources.updateConfiguration(
                config,
                requireActivity().baseContext.resources.displayMetrics
            )
        }
    }
}