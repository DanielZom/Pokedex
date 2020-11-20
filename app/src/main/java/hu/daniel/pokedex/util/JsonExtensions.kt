package hu.daniel.pokedex.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import hu.daniel.pokedex.moshi

fun List<*>.toJson(clazz: Class<*>): String {
    val type = Types.newParameterizedType(MutableList::class.java, clazz)
    val adapter = moshi.adapter<List<*>>(type)
    return adapter.toJson(this)
}

fun String.fromJsonToList(clazz: Class<*>): List<*>? {
    val type = Types.newParameterizedType(MutableList::class.java, clazz)
    val adapter = moshi.adapter<List<*>>(type)
    return adapter.fromJson(this)
}

fun <T> T?.toJson(clazz: Class<T>): String {
    val adapter: JsonAdapter<T> = moshi.adapter(clazz)
    return adapter.toJson(this) ?: ""
}

fun <T> String.fromJson(clazz: Class<T>): T? {
    val adapter: JsonAdapter<T> = moshi.adapter(clazz)
    return adapter.fromJson(this)
}