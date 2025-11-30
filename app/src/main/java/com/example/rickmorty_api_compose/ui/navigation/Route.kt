package com.example.rickmorty_api_compose.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.rickmorty_api_compose.ui.detail.CharacterDetailScreen
import com.example.rickmorty_api_compose.ui.list.CharacterListScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route:String) {
    @Serializable
    data object List:Route("character_list")
    @Serializable
    data class Detail(val id:Long):Route(route = "character_detail[$id]")
}

fun NavController.navigateToCharacterDetails(id:Long) {
    this.navigate(Route.Detail(id))
}

fun NavGraphBuilder.characterDetailDestination(
    modifier:Modifier = Modifier,
) {
    composable<Route.Detail> {


            backStackEntry ->
        CharacterDetailScreen(
            modifier = modifier,
        )


    }
}

fun NavGraphBuilder.characterListDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit
) {
    composable<Route.List> {

        CharacterListScreen(modifier = modifier,
            onShowDetail = {
                    id ->
                onNavigateToDetails(id)
            })


    }
}