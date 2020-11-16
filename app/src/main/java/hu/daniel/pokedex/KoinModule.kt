package hu.daniel.pokedex

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.daniel.pokedex.presentation.PokemonViewModel
import hu.daniel.pokedex.repository.network.ApiService
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val KoinModule = module {
    single { provideRetrofitClient(get(), get()) }
    single { buildNewsOKHTTPClient() }
    single { provideMoshi() }
    single { provideService(get()) }
    viewModel { PokemonViewModel(get()) }
}

private fun provideMoshi() = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideRetrofitClient(moshi: Moshi, client: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .build()

private fun provideService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

private fun buildNewsOKHTTPClient() = OkHttpClient
    .Builder()
    .addNetworkInterceptor(StethoInterceptor())
    .build()