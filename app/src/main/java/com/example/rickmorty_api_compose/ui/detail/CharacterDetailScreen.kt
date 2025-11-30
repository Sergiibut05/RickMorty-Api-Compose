package com.example.rickmorty_api_compose.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterDetailScreen(
        modifier = modifier,
        name = uiState.name,
        image = uiState.image
    )

}

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    name: String,
    image: String?,
){
    Column(modifier = modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally) {
        if (image != null)  {

            AsyncImage(contentDescription = name,
                model = image)

        }
        Text(text = name)
    }
}