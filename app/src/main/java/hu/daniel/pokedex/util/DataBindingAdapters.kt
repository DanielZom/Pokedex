package hu.daniel.pokedex.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import hu.daniel.pokedex.R


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(this)
            .load(url)
            .error(R.drawable.pokemon_placeholder)
            .placeholder(R.drawable.pokemon_placeholder)
            .into(this)
}

@BindingAdapter("visibleBy")
fun View.visibleBy(loadState: LoadState) {
    visibility = if (loadState == LoadState.Loading) VISIBLE else GONE
}

@BindingAdapter("goneBy")
fun View.goneBy(loadState: LoadState) {
    visibility = if (loadState != LoadState.Loading) VISIBLE else GONE
}

