package hu.daniel.pokedex.repository

import hu.daniel.pokedex.domain.PokemonDetail
import hu.daniel.pokedex.domain.ResponseData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ResponseData

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonDetail
}