package hu.daniel.pokedex.repository.network

data class PokemonAbilityWrapper(val ability: PokemonAbility)

data class PokemonAbility(val name: String, val url: String)