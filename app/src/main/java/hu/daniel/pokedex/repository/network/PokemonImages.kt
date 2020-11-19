package hu.daniel.pokedex.repository.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonImages(
    @Json(name = "front_shiny") var shinyFront: String?,
    @Json(name = "back_shiny") var shinyBack: String?,
    @Json(name = "front_default") var defaultFront: String?,
    @Json(name = "back_default") var defaultBack: String?
)