package com.example.nasajm.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nasajm.R
import com.example.nasajm.databinding.SettingsFragmentBinding
import com.example.nasajm.storages.ThemeStorage
import com.example.nasajm.viewBinding
import kotlinx.coroutines.flow.collect

class SettingsFragment : Fragment(R.layout.settings_fragment), View.OnClickListener {

    private val THEME_PURPLE = 0
    private val THEME_GREEN = 1

    private val viewBinding: SettingsFragmentBinding by viewBinding(
        SettingsFragmentBinding::bind
    )

    private val viewModel: SettingsViewModel by viewModels {
        SettingViewModelFactory(ThemeStorage(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.themePurple.setOnClickListener(this)
        viewBinding.themeGreen.setOnClickListener(this)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.themeId.collect {
                when (it) {
                    THEME_PURPLE -> {
                        viewBinding.themePurple.isSelected = true
                    }
                    THEME_GREEN -> {
                        viewBinding.themeGreen.isSelected = true
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.theme_purple -> {
                    viewModel.setTheme(THEME_PURPLE)
                    requireActivity().recreate()
                }
                R.id.theme_green -> {
                    viewModel.setTheme(THEME_GREEN)
                    requireActivity().recreate()
                }
            }
        }
    }
}

class SettingViewModelFactory(
    private val themeStorage: ThemeStorage
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SettingsViewModel(themeStorage) as T
}