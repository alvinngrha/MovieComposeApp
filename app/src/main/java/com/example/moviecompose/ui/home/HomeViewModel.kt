package com.example.moviecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.data.repository.MovieRepository
import com.example.moviecompose.data.response.ResultsItem
import com.example.moviecompose.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<UiState<List<ResultsItem>>>(UiState.Loading)
    val homeUiState: StateFlow<UiState<List<ResultsItem>>> = _homeUiState.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        _homeUiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movies = repository.getMovies(pages = 2)
                _homeUiState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _homeUiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}