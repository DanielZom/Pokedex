package hu.daniel.pokedex.presentation.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.pokedex.R
import hu.daniel.pokedex.presentation.PokemonViewModel
import hu.daniel.pokedex.util.noop
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonListFragment : Fragment() {

    private val viewModel: PokemonViewModel by sharedViewModel()
    private val listAdapter by lazy { PokemonListAdapter(viewModel, this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        startListItemImageUpdateObserving()
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startStateObserving()
        configureList(view)
    }

    private fun startStateObserving() {
        viewModel.state
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun configureList(parentView: View) {
        parentView.findViewById<RecyclerView>(R.id.pokemonList).apply {
            adapter = listAdapter.withLoadStateFooter(footer = PokemonListLoader { listAdapter.retry() })
        }

        lifecycleScope.launch {
            viewModel.pokemons?.collect { listAdapter.submitData(it) }
        }
    }

    private fun handleState(state: PokemonViewModel.State) {
        when (state) {
            PokemonViewModel.State.Idle -> noop(state.toString())
            PokemonViewModel.State.UserNavigatedToDetail -> noop(state.toString())
            PokemonViewModel.State.UserCameBackFromDetail -> noop(state.toString())
        }
    }

    private fun startListItemImageUpdateObserving() {
        lifecycleScope.launchWhenCreated {
            viewModel.onUpdatePokemonImage { listAdapter.updateImageOfItemBy(it.first, it.second) }
        }
    }
}