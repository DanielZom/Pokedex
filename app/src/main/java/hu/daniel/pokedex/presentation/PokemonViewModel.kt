package hu.daniel.pokedex.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import hu.daniel.pokedex.presentation.pokemonlist.POKEMON_API_PAGE_SIZE
import hu.daniel.pokedex.presentation.pokemonlist.PokemonPagingSource
import hu.daniel.pokedex.repository.network.PokemonDetail
import hu.daniel.pokedex.repository.network.PokemonListItem
import hu.daniel.pokedex.repository.network.ApiService
import hu.daniel.pokedex.util.noop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModel(private val service: ApiService) : ViewModel(), KoinComponent {
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
                Intent.UserClicksBackOnDetail -> stateFlow.value = State.UserCameBackFromDetail
                Intent.Idle -> stateFlow.value = State.Idle
            }
        }
    }

    fun startDownloadPokemonData() {
        pokemons = Pager(PagingConfig(pageSize = POKEMON_API_PAGE_SIZE)) {
            PokemonPagingSource(service)
        }.flow.cachedIn(viewModelScope)
    }

    fun downloadAndPersistPokemonBy(pokemonListItem: PokemonListItem, position: Int) {
        val uri = Uri.parse(pokemonListItem.url)
        val pathParams = uri.path?.split("/") ?: return
        val pokemonId = pathParams[pathParams.lastIndex - 1].toIntOrNull() ?: return
        viewModelScope.launch {
            val response = service.getPokemonById(pokemonId)
            //TODO persist
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
        object UserClicksBackOnDetail : Intent()
        object UserClicksOnListItem : Intent() {
            var selectedListItem: PokemonDetail? = null
        }
    }

    sealed class State {
        object Idle : State()
        object UserCameBackFromDetail : State()
        object UserNavigatedToDetail : State() {
            var selectedListItem: PokemonDetail? = null
        }
    }
}