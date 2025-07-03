package com.example.moviecompose.data.api

import com.example.moviecompose.data.response.DetailMovieResponse
import com.example.moviecompose.data.response.MovieResponse
import com.example.moviecompose.data.response.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): Response<DetailMovieResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): Response<MovieVideoResponse>
}
