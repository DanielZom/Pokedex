package hu.daniel.pokedex.presentation.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.pokedex.R
import hu.daniel.pokedex.databinding.ComponentPokemonListItemBinding
import hu.daniel.pokedex.presentation.PokemonViewModel
import hu.daniel.pokedex.repository.network.PokemonDetail
import hu.daniel.pokedex.repository.network.PokemonListItem

class PokemonListAdapter(
        private var viewModel: PokemonViewModel,
        private var parent: Fragment
) : PagingDataAdapter<PokemonListItem, PokemonListAdapter.PokemonViewHolder>(PokemonComparator) {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.pokemon = item
        if (item.pokemonDetails == null) viewModel.downloadAndPersistPokemonBy(item, position)
        holder.binding.pokemonLayout.setOnClickListener {
            viewModel.intentChannel.offer(PokemonViewModel.Intent.UserClicksOnListItem.apply {
                selectedListItem = item.pokemonDetails
            })
            parent.findNavController().navigate(R.id.pokemonDetailFragment)
        }
    }

    fun updateImageOfItemBy(position: Int, details: PokemonDetail) {
        val item = getItem(position) ?: return
        item.pokemonDetails = details
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_pokemon_list_item, parent, false))
    }

    class PokemonViewHolder(val binding: ComponentPokemonListItemBinding) : RecyclerView.ViewHolder(binding.root)

    object PokemonComparator : DiffUtil.ItemCallback<PokemonListItem>() {
        override fun areItemsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem): Boolean {
            return oldItem == newItem
        }
    }
}