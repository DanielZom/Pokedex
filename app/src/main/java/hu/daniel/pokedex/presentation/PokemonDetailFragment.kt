package hu.daniel.pokedex.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.pokedex.R
import hu.daniel.pokedex.databinding.FragmentPokemonDetailBinding
import hu.daniel.pokedex.util.noop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonDetailFragment : Fragment() {

    private val viewModel: PokemonViewModel by sharedViewModel()
    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startStateObserving()

        view.findViewById<ImageView>(R.id.back)
            .setOnClickListener { findNavController().navigateUp() }
    }

    private fun startStateObserving() {
        viewModel.state
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: PokemonViewModel.State) {
        when (state) {
            PokemonViewModel.State.Idle -> noop(state.toString())
            PokemonViewModel.State.UserNavigatedToDetail -> {
                binding.pokemon =
                    (state as PokemonViewModel.State.UserNavigatedToDetail).selectedListItem
                viewModel.intentChannel.offer(PokemonViewModel.Intent.Idle)
            }
            PokemonViewModel.State.UserCameBackFromDetail -> noop(state.toString())
        }
    }
}