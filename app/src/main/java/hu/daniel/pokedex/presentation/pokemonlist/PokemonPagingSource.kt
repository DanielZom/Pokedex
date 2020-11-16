package hu.daniel.pokedex.presentation.pokemonlist

import androidx.paging.PagingSource
import hu.daniel.pokedex.domain.PokemonListItem
import hu.daniel.pokedex.repository.ApiService
import hu.daniel.pokedex.repository.pokemon.IPokemonRepository
import timber.log.Timber

const val POKEMON_API_PAGE_SIZE = 50

class PokemonPagingSource(
        private val service: ApiService,
        private val pokemonRepository: IPokemonRepository
) : PagingSource<Int, PokemonListItem>() {
    private val offset = 50

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        val nextPage = params.key ?: 0
        return try {
            val response = service.getPokemons(POKEMON_API_PAGE_SIZE, nextPage)
            LoadResult.Page(
                    data = response.results,
                    prevKey = if (nextPage == 0) null else nextPage - offset,
                    nextKey = nextPage + offset
            )
        } catch (e: Exception) {
            val savedPokemons = pokemonRepository.getPokemons(POKEMON_API_PAGE_SIZE, nextPage)
            if (savedPokemons.isNotEmpty()) {
                LoadResult.Page(
                        data = savedPokemons,
                        prevKey = if (nextPage == 0) null else nextPage - offset,
                        nextKey = nextPage + offset
                )
            } else {
                LoadResult.Error(e)
            }
        }
    }
}