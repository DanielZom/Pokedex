package hu.daniel.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import hu.daniel.pokedex.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.initIntentChannel()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHost).navigateUp()
}