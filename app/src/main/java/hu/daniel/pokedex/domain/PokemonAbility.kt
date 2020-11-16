package hu.daniel.pokedex.domain

data class PokemonAbilityWrapper(val ability: PokemonAbility)

data class PokemonAbility(val name: String, val url: String)