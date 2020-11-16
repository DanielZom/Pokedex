package hu.daniel.pokedex.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hu.daniel.pokedex.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment() {

    private val viewModel: PokemonViewModel by sharedViewModel()

    private val SCREEN_TIME = 3000L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startDownloadPokemonData()
        startScreenTimeout()
    }

    private fun startScreenTimeout() {
        lifecycleScope.launch {
            delay(SCREEN_TIME)
            findNavController().navigate(R.id.action_splashFragment_to_pokemonListFragment)
        }
    }
}