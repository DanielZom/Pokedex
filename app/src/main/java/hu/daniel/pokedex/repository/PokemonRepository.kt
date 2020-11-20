package hu.daniel.pokedex.repository

import android.content.Context
import android.net.Uri
import hu.daniel.pokedex.domain.*
import hu.daniel.pokedex.repository.database.Pokemon
import hu.daniel.pokedex.repository.database.PokemonDatabase
import hu.daniel.pokedex.util.fromJson
import hu.daniel.pokedex.util.fromJsonToList
import java.lang.Exception

const val DEFAULT_PAGING_OFFSET = 50
const val POKEMON_API_PAGE_SIZE = 50

interface IPokemonRepository {
    suspend fun savePokemon(pokemonListItem: PokemonListItem)
    suspend fun getPokemonBy(pokemonUrl: String): PokemonDetail?
    suspend fun getPokemons(offset: Int): List<PokemonListItem>?
}

class PokemonRepository(context: Context, private val service: ApiService) : IPokemonRepository {

    private var pokemonDao = PokemonDatabase.getDatabase(context).pokeDao()

    override suspend fun getPokemons(offset: Int): List<PokemonListItem>? {
        return try {
            downloadPokemonsBy(offset).results
        } catch (exp: Exception) {
            val savedPokemons = getSavedPokemonsBy(offset)
            if (savedPokemons.isEmpty()) null else savedPokemons
        }
    }

    override suspend fun getPokemonBy(pokemonUrl: String): PokemonDetail? {
        try {
            val id = pokemonUrl.parsePokemonUrlToId() ?: return null
            return service.getPokemonById(id)
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun savePokemon(pokemonListItem: PokemonListItem) {
        val pokemonToSave = pokemonListItem.toPokemon() ?: return
        pokemonDao.savePokemon(pokemonToSave)
    }

    private suspend fun downloadPokemonsBy(offset: Int) = service.getPokemons(POKEMON_API_PAGE_SIZE, offset)

    private suspend fun getSavedPokemonsBy(offset: Int): List<PokemonListItem> {
        val from: Int
        val to: Int
        if (offset > 0) {
            from = offset - POKEMON_API_PAGE_SIZE
            to = offset
        } else {
            from = 0
            to = POKEMON_API_PAGE_SIZE
        }
        return pokemonDao.getPokemons(from, to).toPokemonListItems()
    }

    private fun String.parsePokemonUrlToId(): Int? {
        val uri = Uri.parse(this)
        val pathParams = uri.path?.split("/") ?: return null
        return pathParams[pathParams.lastIndex - 1].toIntOrNull()
    }
}

@Suppress("UNCHECKED_CAST")
fun List<Pokemon>.toPokemonListItems(): List<PokemonListItem> {
    if (isEmpty()) return arrayListOf()
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