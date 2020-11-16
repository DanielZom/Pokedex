package hu.daniel.pokedex.util

import hu.daniel.pokedex.domain.PokemonAbilityWrapper

fun List<PokemonAbilityWrapper>?.toFormattedList(): String {
    if (isNullOrEmpty()) return "-"
    var formattedList = ""
    forEach { formattedList += "• ${it.ability.name}\n" }
    return formattedList
}

fun Int.formatHeight(): String {
    var formattedWeight = "${this * 10F}"
    if (formattedWeight.endsWith(".0")) formattedWeight = formattedWeight.replace(".0", "")
    return "$formattedWeight cm"
}

fun Int.formatWeight(): String {
    var formattedWeight = "${this / 10F}"
    if (formattedWeight.endsWith(".0")) formattedWeight = formattedWeight.replace(".0", "")
    return "$formattedWeight kg"
}