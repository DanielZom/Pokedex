package hu.daniel.pokedex

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val KoinModule = module {
    single { provideRetrofitClient() }
}

private fun provideRetrofitClient(): RESTService {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(buildNewsOKHTTPClient())
        .build().create(RESTService::class.java)
}

private fun buildNewsOKHTTPClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()
}