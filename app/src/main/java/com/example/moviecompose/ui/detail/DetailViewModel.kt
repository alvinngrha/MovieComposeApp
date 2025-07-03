package com.example.moviecompose.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun fetchDetail(movieId: Int) {
        _uiState.value = DetailUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getMovieDetail(movieId)
                if (response.isSuccessful) {
                    val detail = response.body()
                    if (detail != null) {
                        _uiState.value = DetailUiState.Success(detail)
                    } else {
                        _uiState.value = DetailUiState.Error("No data found")
                    }
                } else {
                    _uiState.value = DetailUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

class DetailViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
