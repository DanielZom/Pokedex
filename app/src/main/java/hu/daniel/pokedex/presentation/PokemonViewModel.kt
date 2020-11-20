package hu.daniel.pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import hu.daniel.pokedex.presentation.pokemonlist.PokemonPagingSource
import hu.daniel.pokedex.domain.PokemonDetail
import hu.daniel.pokedex.domain.PokemonListItem
import hu.daniel.pokedex.repository.IPokemonRepository
import hu.daniel.pokedex.repository.POKEMON_API_PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModel(
        private val pokemonRepository: IPokemonRepository
) : ViewModel() {

    var pokemons: Flow<PagingData<PokemonListItem>>? = null
    val intentChannel = Channel<Intent>(Channel.UNLIMITED)
    val state: StateFlow<State>
        get() = stateFlow

    private var updatePokemonImage = Channel<Pair<Int, PokemonDetail>>(Channel.UNLIMITED)
    private val stateFlow = MutableStateFlow<State>(State.Idle)

    fun initIntentChannel() {
        viewModelScope.launch { handleIntents() }
    }

    private suspend fun handleIntents() {
        intentChannel.consumeAsFlow().collect {
            when (it) {
                Intent.UserClicksOnListItem -> {
                    stateFlow.value = State.UserNavigatedToDetail.apply {
                        selectedListItem = (it as Intent.UserClicksOnListItem).selectedListItem
                    }
                }
                Intent.Idle -> stateFlow.value = State.Idle
            }
        }
    }

    fun startDownloadPokemonData() {
        pokemons = Pager(PagingConfig(pageSize = POKEMON_API_PAGE_SIZE)) {
            PokemonPagingSource(pokemonRepository)
        }.flow.cachedIn(viewModelScope)
    }

    fun downloadAndPersistPokemonBy(pokemonListItem: PokemonListItem, position: Int) {
        viewModelScope.launch {
            val response = pokemonRepository.getPokemonBy(pokemonListItem.url) ?: return@launch
            pokemonRepository.savePokemon(pokemonListItem.apply { pokemonDetails = response })
            updatePokemonImage.send(Pair(position, response))
        }
    }

    suspend fun onUpdatePokemonImage(updateListener: (Pair<Int, PokemonDetail>) -> Unit) {
        updatePokemonImage.consumeAsFlow().collect {
            updateListener.invoke(it)
        }
    }

    sealed class Intent {
        object Idle : Intent()
        object UserClicksOnListItem : Intent() {
            var selectedListItem: PokemonDetail? = null
        }
    }

    sealed class State {
        object Idle : State()
        object UserNavigatedToDetail : State() {
            var selectedListItem: PokemonDetail? = null
        }
    }
}