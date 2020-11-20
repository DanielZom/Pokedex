package hu.daniel.pokedex.domain

import com.squareup.moshi.Json

data class PokemonDetail(
        val id: Int,
        val name: String,
        val weight: Int,
        val height: Int,
        @Json(name = "sprites") val images: PokemonImages?,
        val types: List<PokemonTypeWrapper>,
        val abilities: List<PokemonAbilityWrapper>
)