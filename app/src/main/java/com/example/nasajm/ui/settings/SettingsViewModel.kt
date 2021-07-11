package com.example.nasajm.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasajm.storages.ThemeStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeStorage: ThemeStorage) : ViewModel() {

    private val _themeId = MutableStateFlow<Int>(0)
    val themeId: Flow<Int> = _themeId

    init {
        viewModelScope.launch {
            _themeId.value = themeStorage.themeId
        }
    }

    fun setTheme(themeId: Int) {
        themeStorage.themeId = themeId
        _themeId.value = themeId
    }
}