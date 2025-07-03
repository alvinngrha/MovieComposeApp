package com.example.moviecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviecompose.data.repository.MovieRepository
import com.example.moviecompose.ui.detail.DetailScreen
import com.example.moviecompose.ui.home.HomeScreen
import com.example.moviecompose.ui.theme.MovieComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieHome(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieHome(modifier: Modifier) {
    val navController = androidx.navigation.compose.rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                repository = MovieRepository(),
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                }
            )
        }
        composable("detail/{movieId}") { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: return@composable
            DetailScreen(
                movieId = movieId,
                repository = MovieRepository(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

