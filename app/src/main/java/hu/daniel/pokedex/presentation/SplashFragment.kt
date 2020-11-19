package hu.daniel.pokedex.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hu.daniel.pokedex.BuildConfig
import hu.daniel.pokedex.R
import hu.daniel.pokedex.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    private val viewModel: PokemonViewModel by sharedViewModel()

    private val SCREEN_TIME = 3000L

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        enableFullscreenMode()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        versionNumber.text = BuildConfig.VERSION_NAME
        viewModel.startDownloadPokemonData()
        splashImage.onViewRendered {
            startScreenTimeout()
            animateSplashImage()
        }
        splashImage.setOnClickListener {
            animateSplashImage()
        }
    }

    private fun animateSplashImage() {
        val config = ShakingAnimationConfiguration(2, 300L, 1000L, lifecycleScope)
        splashImage.startShakingAnimation(config) {
            lifecycleScope.launch {
                splashImage.setImageResource(R.drawable.pokeball_half_opened)
                delay(300L)
                splashImage.setImageResource(R.drawable.pokeball_fully_opened)
                splashImage.startScaleAnimation(400) {
                    fadeLayer?.alpha = 1F
                }
            }
        }
    }

    private fun startScreenTimeout() {
        lifecycleScope.launch {
            delay(SCREEN_TIME)
            findNavController().navigate(R.id.action_splashFragment_to_pokemonListFragment)
        }
    }

    @Suppress("DEPRECATION")
    private fun enableFullscreenMode() {
        activity?.window?.decorView?.systemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}