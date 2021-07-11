package com.example.nasajm.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasajm.R
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.domain.PictureOfTheDay
import com.example.nasajm.domain.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val app: Application,
    private val repository: NasaRepositoryImp
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<String>()
    private val _data = MutableStateFlow(PictureOfTheDay())

    val loading: Flow<Boolean> = _loading
    val error: Flow<String> = _error
    val data: Flow<PictureOfTheDay> = _data

    fun fetchPictureOfTheDay(minusDaysFromToday: Long) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getPictureOfTheDay(minusDaysFromToday)
            _loading.value = false

            when (result) {
                is Success -> {
                    _data.value = result.value
                }
                is Error -> {
                    _error.emit(app.getString(R.string.api_load_error))
                }
                else -> {
                    _error.emit(app.getString(R.string.api_load_error))
                }
            }
        }
    }

}