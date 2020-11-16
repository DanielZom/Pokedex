package hu.daniel.pokedex.repository.pokemon

import android.content.Context
import hu.daniel.pokedex.domain.*
import hu.daniel.pokedex.repository.database.Pokemon
import hu.daniel.pokedex.repository.database.PokemonDatabase
import hu.daniel.pokedex.util.fromJson
import hu.daniel.pokedex.util.fromJsonToList
import timber.log.Timber

interface IPokemonRepository {
    suspend fun savePokemon(pokemonListItem: PokemonListItem)
    suspend fun getPokemons(limit: Int, offset: Int): List<PokemonListItem>
}

class PokemonRepository(context: Context) : IPokemonRepository {

    var pokemonDao = PokemonDatabase.getDatabase(context).pokeDao()

    override suspend fun savePokemon(pokemonListItem: PokemonListItem) {
        val pokemonToSave = pokemonListItem.toPokemon() ?: return
        pokemonDao.savePokemon(pokemonToSave)
    }

    override suspend fun getPokemons(limit: Int, offset: Int): List<PokemonListItem> {
        val from: Int
        val to: Int
        if (offset > 0) {
            from = offset - limit
            to = offset
        } else {
            from = 0
            to = limit
        }
        val savedPokemons = pokemonDao.getPokemons(from, to)
        return if (savedPokemons.isEmpty()) arrayListOf() else savedPokemons.toPokemonListItems()
    }
}

@Suppress("UNCHECKED_CAST")
fun List<Pokemon>.toPokemonListItems(): List<PokemonListItem> {
    return map {
        val details = PokemonDetail(
                it.id,
                it.name,
                it.weight,
                it.height,
                it.imagesJson.fromJson(PokemonImages::class.java),
                it.typesJson.fromJsonToList(PokemonTypeWrapper::class.java) as List<PokemonTypeWrapper>,
                it.abilitiesJson.fromJsonToList(PokemonAbilityWrapper::class.java) as List<PokemonAbilityWrapper>,
        )
        PokemonListItem(
                it.name,
                it.url,
                details
        )
    }
}