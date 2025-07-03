package com.example.moviecompose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecompose.data.repository.MovieRepository

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun DetailScreen(
    movieId: Int,
    repository: MovieRepository,
    modifier: Modifier = Modifier
) {
    val viewModel: DetailViewModel = remember {
        DetailViewModelFactory(repository).create(DetailViewModel::class.java)
    }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchDetail(movieId)
    }

    when (uiState) {
        is DetailUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailUiState.Error -> {
            val message = (uiState as DetailUiState.Error).message
            Text(text = message, color = MaterialTheme.colorScheme.error)
        }
        is DetailUiState.Success -> {
            val detail = (uiState as DetailUiState.Success).detail
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(IMAGE_BASE_URL + detail.poster_path),
                    contentDescription = detail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = detail.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Release: ${detail.release_date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = detail.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
