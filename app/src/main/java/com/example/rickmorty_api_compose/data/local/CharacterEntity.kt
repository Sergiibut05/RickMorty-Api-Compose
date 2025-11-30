package com.example.rickmorty_api_compose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickmorty_api_compose.data.model.Character

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val image: String,
    val originName: String
)

/* MAPPERS */
fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        image = this.image,
        originName = this.originName
    )
}

fun List<CharacterEntity>.toModel(): List<Character> {
    return this.map {
        Character(
            id = it.id,
            name = it.name,
            image = it.image,
            originName = it.originName
        )
    }
}

fun CharacterEntity.toModel(): Character {
    return Character(
        id = this.id,
        name = this.name,
        image = this.image,
        originName = this.originName
    )
}