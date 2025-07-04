package com.example.moviecompose.data.repository

import com.example.moviecompose.data.api.ApiConfig
import com.example.moviecompose.data.response.DetailMovieResponse
import com.example.moviecompose.data.response.MovieVideoResponse
import com.example.moviecompose.data.response.ResultsItem
import retrofit2.Response

class MovieRepository {
    suspend fun getMovies(pages: Int = 1): List<ResultsItem> {
        val allMovies = mutableListOf<ResultsItem>()
        for (page in 1..pages) {
            val response = ApiConfig.getApiService().getMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results ?: emptyList()
                allMovies.addAll(movies)
            }
        }
        return allMovies
    }

    suspend fun getMovieDetail(movieId: Int): Response<DetailMovieResponse> {
        return ApiConfig.getApiService().getMovieDetail(movieId)
    }

    suspend fun getMovieVideos(movieId: Int): Response<MovieVideoResponse> {
        return ApiConfig.getApiService().getMovieVideos(movieId)
    }
}
