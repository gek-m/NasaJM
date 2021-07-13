package com.example.nasajm.ui.pages.earth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasajm.R
import com.example.nasajm.domain.NasaRepositoryImp
import com.example.nasajm.domain.Success
import com.example.nasajm.network.getRandomPictureUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EarthViewModel(
    private val app: Application,
    private val repository: NasaRepositoryImp
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<String>()
    private val _url = MutableStateFlow("")

    val loading: Flow<Boolean> = _loading
    val error: Flow<String> = _error
    val url: Flow<String> = _url

    fun fetchEarthPhotoUrl() {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getEarthPictures()
            _loading.value = false

            when (result) {
                is Success -> {
                    _url.value = result.value.getRandomPictureUrl()
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