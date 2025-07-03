package com.example.moviecompose.data.repository

import com.example.moviecompose.data.api.ApiConfig
import com.example.moviecompose.data.response.DetailMovieResponse
import com.example.moviecompose.data.response.MovieResponse
import retrofit2.Response

class MovieRepository {
    suspend fun getMovies(): Response<MovieResponse> {
        return ApiConfig.getApiService().getMovies()
    }

    suspend fun getMovieDetail(movieId: Int): Response<DetailMovieResponse> {
        return ApiConfig.getApiService().getMovieDetail(movieId)
    }
}
