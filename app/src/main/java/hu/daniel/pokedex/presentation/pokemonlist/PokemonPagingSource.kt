package hu.daniel.pokedex.presentation.pokemonlist

import androidx.paging.PagingSource
import hu.daniel.pokedex.repository.network.PokemonListItem
import hu.daniel.pokedex.repository.network.ApiService

const val POKEMON_API_PAGE_SIZE = 50

class PokemonPagingSource(
    private val service: ApiService
) : PagingSource<Int, PokemonListItem>() {
    private val offset = 50

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        return try {
            val nextPage = params.key ?: offset

            val response = service.getPokemons(POKEMON_API_PAGE_SIZE, nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == offset) null else nextPage - offset,
                nextKey = nextPage + offset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}