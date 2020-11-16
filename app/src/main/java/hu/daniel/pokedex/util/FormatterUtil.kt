package hu.daniel.pokedex.util

import hu.daniel.pokedex.repository.network.PokemonAbilityWrapper

fun List<PokemonAbilityWrapper>?.toFormattedList(): String {
    if (isNullOrEmpty()) return "-"
    var formattedList = ""
    forEach { formattedList += "• ${it.ability.name}\n" }
    return formattedList
}