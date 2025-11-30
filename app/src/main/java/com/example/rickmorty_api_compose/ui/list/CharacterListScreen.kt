package com.example.rickmorty_api_compose.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    onShowDetail:(Long)->Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    when(uiState) {
        is ListUiState.Initial -> {

        }
        is ListUiState.Loading -> {
            CharacterLoadingScreen(modifier)
        }
        is ListUiState.Success -> {
            CharacterList(modifier, uiState, onShowDetail)
        }
        is ListUiState.Error -> {
            CharacterError()
        }
    }
}

@Composable
private fun CharacterError(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Se ha producido un error", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun CharacterLoadingScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingIndicator(
            modifier = Modifier.size(128.dp)
        )
    }
}

@Composable
private fun CharacterList(
    modifier: androidx.compose.ui.Modifier,
    uiState: ListUiState,
    onShowDetail: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        items(
            items = (uiState as ListUiState.Success).characters,
            key = { item ->
                item.id
            }
        )
        {
            CharacterListItemCard(
                characterId = it.id,
                name = it.name,
                image = it.image,
                onShowDetail = onShowDetail
            )
        }
    }
}

@Composable
fun CharacterListItemCard(
    modifier: Modifier = Modifier,
    characterId: Long,
    name: String,
    image: String,
    onShowDetail: (Long) -> Unit,
)
{
    Card(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable(
                enabled = true,
                onClick = {
                    onShowDetail(characterId)
                })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                modifier = androidx.compose.ui.Modifier.size(64.dp),
                model = image,
                contentDescription = name,
                contentScale = ContentScale.Fit
            )
            Text(text= name,
                style = MaterialTheme.typography.headlineSmall)
        }

    }
}