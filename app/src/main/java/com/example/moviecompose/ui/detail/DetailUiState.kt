package com.example.moviecompose.ui.detail

import com.example.moviecompose.data.response.DetailMovieResponse

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val detail: DetailMovieResponse) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}