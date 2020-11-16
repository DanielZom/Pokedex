package hu.daniel.pokedex.domain

import hu.daniel.pokedex.repository.database.Pokemon
import hu.daniel.pokedex.util.toJson

data class ResponseData(val results: List<PokemonListItem>)

data class PokemonListItem(var name: String, var url: String, var pokemonDetails: PokemonDetail? = null)

fun PokemonListItem.toPokemon(): Pokemon? {
    if (pokemonDetails == null) return null
    return Pokemon(
            pokemonDetails!!.id,
            url,
            pokemonDetails!!.name,
            pokemonDetails!!.weight,
            pokemonDetails!!.height,
            pokemonDetails!!.images.toJson(PokemonImages::class.java),
            pokemonDetails!!.types.toJson(PokemonTypeWrapper::class.java),
            pokemonDetails!!.abilities.toJson(PokemonAbilityWrapper::class.java),
    )
}