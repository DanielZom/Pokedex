package hu.daniel.pokedex.util

import android.graphics.PointF
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ShakingAnimationConfiguration(var repeatCount: Int, var repeatDelay: Long, var overallDuration: Long, var lifecycleScope: LifecycleCoroutineScope)

fun View.startShakingAnimation(config: ShakingAnimationConfiguration, endListener: () -> Unit) {
    var repeated = 1
    val pivot = PointF(width * 0.5F, height * 0.5F)
    //The oscillation is 40 degrees
    val rotateDuration = (config.overallDuration * 0.4F).toLong()
    val rotateLeft = createRotateAnimation(rotateDuration, 360F, 320F, pivot)
    val rotateRight = createRotateAnimation(rotateDuration, 320F, 400F, pivot)
    val rotateBackToCenter = createRotateAnimation(rotateDuration / 2, 40F, 0F, pivot)
    rotateLeft.onAnimationEnd { startAnimation(rotateRight) }
    rotateRight.onAnimationEnd { startAnimation(rotateBackToCenter) }
    rotateBackToCenter.onAnimationEnd {
        if (repeated != config.repeatCount) {
            repeated++
            config.lifecycleScope.launch {
                delay(config.repeatDelay)
                startAnimation(rotateLeft)
            }
        } else {
            endListener.invoke()
        }
    }
    startAnimation(rotateLeft)
}

fun View.startScaleAnimation(duration: Long, minSizeInPercent: Float = 1F, maxSizeInPercent: Float = 7F, endListener: () -> Unit) {
    val scaleUpAnimation = createScaleAnimation(minSizeInPercent, maxSizeInPercent, duration)
    scaleUpAnimation.onAnimationEnd {
        layoutParams = ConstraintLayout.LayoutParams(width * maxSizeInPercent.toInt(), height * maxSizeInPercent.toInt())
        endListener.invoke()
    }
    startAnimation(scaleUpAnimation)
}

fun View.startFadeAnimation(fadeDuration: Long) {
    startAnimation(AlphaAnimation(1F, 0F).apply { duration = fadeDuration })
}

private fun createScaleAnimation(fromSizeInPercent: Float, toSizeInPercent: Float, duration: Long): ScaleAnimation {
    val animation = ScaleAnimation(fromSizeInPercent, toSizeInPercent,
        fromSizeInPercent, toSizeInPercent,
        Animation.RELATIVE_TO_SELF, 0.5F,
        Animation.RELATIVE_TO_SELF, 0.5F
    )
    animation.duration = duration
    return animation
}

private fun createRotateAnimation(duration: Long, fromDegree: Float, toDegree: Float, pivot: PointF): RotateAnimation {
    val rotateAnimation = RotateAnimation(fromDegree, toDegree, pivot.x, pivot.y)
    rotateAnimation.duration = duration
    return rotateAnimation
}

fun Animation.onAnimationEnd(listener: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = noop()
        override fun onAnimationRepeat(animation: Animation?) = noop()
        override fun onAnimationEnd(animation: Animation?) {
            listener.invoke()
        }
    })
}