package hu.daniel.pokedex.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "weight")
        val weight: Int,
        @ColumnInfo(name = "height")
        val height: Int,
        @ColumnInfo(name = "imagesJson")
        val imagesJson: String,
        @ColumnInfo(name = "typesJson")
        val typesJson: String,
        @ColumnInfo(name = "abilitiesJson")
        val abilitiesJson: String
)