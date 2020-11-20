package hu.daniel.pokedex.presentation.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.pokedex.R
import hu.daniel.pokedex.databinding.ComponentPokemonListItemBinding
import hu.daniel.pokedex.databinding.ComponentPokemonListLoaderBinding

class PokemonListLoader(
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonListLoader.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.loadState = loadState
        holder.binding.retry.setOnClickListener { retry.invoke() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_pokemon_list_loader, parent, false))
    }

    class LoadStateViewHolder(val binding: ComponentPokemonListLoaderBinding) : RecyclerView.ViewHolder(binding.root)
}