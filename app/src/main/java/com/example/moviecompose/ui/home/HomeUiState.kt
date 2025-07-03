package com.example.moviecompose.ui.home

import com.example.moviecompose.data.response.ResultsItem

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val movies: List<ResultsItem>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}