package hu.daniel.pokedex.repository.database

import android.content.Context
import androidx.room.*

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        private var instance: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, PokemonDatabase::class.java.name)
                        .build()
            }
            return instance!!
        }
    }

    abstract fun pokeDao(): PokemonDao
}