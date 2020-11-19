package hu.daniel.pokedex.repository.network

data class ResponseData(val results: List<PokemonListItem>)

data class PokemonListItem(var name: String, var url: String, var pokemonDetails: PokemonDetail? = null)