package hu.daniel.pokedex

import android.content.Context
import androidx.room.*

@Database(entities = [Pokemon::class], version = 1)
abstract class PokeDatabase : RoomDatabase() {
    companion object {
        private var instance: PokeDatabase? = null

        fun getDatabase(context: Context): PokeDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context.applicationContext, PokeDatabase::class.java, PokeDatabase::class.java.name)
                        .build()
            }
            return instance!!
        }
    }

    abstract fun pokeDao(): PokemonDao
}