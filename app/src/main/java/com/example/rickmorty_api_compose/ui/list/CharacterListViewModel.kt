package com.example.rickmorty_api_compose.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty_api_compose.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.rickmorty_api_compose.data.model.Character
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<ListUiState> =
        MutableStateFlow(value = ListUiState.Initial)

    val uiState: StateFlow<ListUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            repository.observe().collect { result ->
                if (result.isSuccess) {
                    val character = result.getOrNull()!!
                    val uiCharacters = character.asListUiState()
                    _uiState.value = ListUiState.Success(uiCharacters)

                } else {
                    _uiState.value = ListUiState.Error
                }
            }
        }
    }
    fun onDeleteCharacter(id: Long){
        viewModelScope.launch {
            val characterResult = repository.readOne(id)
            val character = characterResult.getOrNull()
            if(character != null){
                repository.deleteOne(character)
            }

        }
    }
    fun onDeleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
    fun onSearch(text: String){
        viewModelScope.launch {
            repository.deleteAll()
            val charResponse =  repository.readAll(text).getOrNull()
            if (charResponse != null){
                repository.addAll(charResponse)
            }

        }
    }
}

sealed class ListUiState {
    object Initial: ListUiState()
    object Loading: ListUiState()
    object Error: ListUiState()
    data class Success(
        val characters: List<ListItemUiState>
    ): ListUiState()
}

/**
 * [MODELO DE UI]
 * A veces el modelo de Dominio (Pokemon) tiene más datos de los que la lista necesita (o menos).
 * Creamos un modelo específico para la UI para formatear datos (ej. mayúsculas, fechas, concatenaciones).
 */
data class ListItemUiState(
    val id: Long,
    val name: String,
    val image: String,
)

/**
 * [MAPPERS DE UI]
 * Transforman objetos de Dominio -> Objetos de UI.
 */
fun Character.asListItemUiState(): ListItemUiState {
    return ListItemUiState(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        image = this.image
    )
}

fun List<Character>.asListUiState(): List<ListItemUiState> = this.map(Character::asListItemUiState)