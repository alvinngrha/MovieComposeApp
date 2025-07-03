package com.example.moviecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _Home_uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _Home_uiState.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        _Home_uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getMovies()
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _Home_uiState.value = HomeUiState.Success(movies)
                } else {
                    _Home_uiState.value = HomeUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _Home_uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
