package hu.daniel.pokedex.presentation.pokemonlist

import androidx.paging.PagingSource
import hu.daniel.pokedex.domain.PokemonListItem
import hu.daniel.pokedex.repository.DEFAULT_PAGING_OFFSET
import hu.daniel.pokedex.repository.IPokemonRepository
import java.lang.RuntimeException


class PokemonPagingSource(
        private val pokemonRepository: IPokemonRepository
) : PagingSource<Int, PokemonListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        val nextPage = params.key ?: 0
        val pokemons = pokemonRepository.getPokemons(nextPage)
        return when {
            pokemons == null -> LoadResult.Error(RuntimeException())
            pokemons.isEmpty() -> LoadResult.Page(arrayListOf(), null, null)
            else -> {
                LoadResult.Page(
                    data = pokemons,
                    prevKey = if (nextPage == 0) null else nextPage - DEFAULT_PAGING_OFFSET,
                    nextKey = nextPage + DEFAULT_PAGING_OFFSET
                )
            }
        }
    }
}