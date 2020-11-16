package hu.daniel.pokedex.presentation.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import hu.daniel.pokedex.R
import hu.daniel.pokedex.presentation.PokemonViewModel
import hu.daniel.pokedex.util.noop
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PokemonListFragment : Fragment() {

    private val viewModel: PokemonViewModel by sharedViewModel()
    private val listAdapter by lazy { PokemonListAdapter(viewModel, this) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disableFullscreenMode()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        startListItemImageUpdateObserving()
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startStateObserving()
        configureList()
    }

    private fun startStateObserving() {
        viewModel.state
                .onEach { state -> handleState(state) }
                .launchIn(lifecycleScope)
    }

    private fun configureList() {
        pokemonList.apply {
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                    PokemonListLoader { listAdapter.retry() },
                    PokemonListLoader { listAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.pokemons?.collect {
                listAdapter.submitData(it)
            }
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

    @Suppress("DEPRECATION")
    private fun disableFullscreenMode() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}