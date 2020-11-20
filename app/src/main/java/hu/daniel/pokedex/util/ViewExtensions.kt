package hu.daniel.pokedex.util

import android.view.View
import android.view.ViewTreeObserver

fun View.onViewRendered(renderListener: (View) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            renderListener.invoke(this@onViewRendered)
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}