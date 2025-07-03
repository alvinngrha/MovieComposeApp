package com.example.moviecompose.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.data.repository.MovieRepository
import com.example.moviecompose.data.response.DetailMovieResponse
import com.example.moviecompose.data.response.MovieVideoResponse
import com.example.moviecompose.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailWithVideos(
    val detail: DetailMovieResponse,
    val videos: MovieVideoResponse
)

class DetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _detailUiState = MutableStateFlow<UiState<DetailWithVideos>>(UiState.Loading)
    val detailUiState: StateFlow<UiState<DetailWithVideos>> = _detailUiState.asStateFlow()

    fun fetchDetail(movieId: Int) {
        _detailUiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val detailResponse = repository.getMovieDetail(movieId)
                val videosResponse = repository.getMovieVideos(movieId)
                if (detailResponse.isSuccessful && videosResponse.isSuccessful) {
                    val detail = detailResponse.body()
                    val videos = videosResponse.body()
                    if (detail != null && videos != null) {
                        _detailUiState.value = UiState.Success(DetailWithVideos(detail, videos))
                    } else {
                        _detailUiState.value = UiState.Error("No data found")
                    }
                } else {
                    _detailUiState.value = UiState.Error("Error: ${detailResponse.code()} / ${videosResponse.code()}")
                }
            } catch (e: Exception) {
                _detailUiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
