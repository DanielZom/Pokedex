package hu.daniel.pokedex.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import hu.daniel.pokedex.R


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 20f
        setColorSchemeColors(ContextCompat.getColor(context, R.color.yellow))
        start()
    }

    Glide.with(this)
        .load(url)
        .placeholder(circularProgressDrawable)
        .fallback(circularProgressDrawable)
        .error(R.drawable.pokemon_placeholder)
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

