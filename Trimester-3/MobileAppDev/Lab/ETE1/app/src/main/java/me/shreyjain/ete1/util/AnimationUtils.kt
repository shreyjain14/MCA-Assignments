package me.shreyjain.ete1.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.airbnb.lottie.LottieAnimationView
import kotlin.math.hypot

/**
 * Utility class for animations throughout the app
 */
object AnimationUtils {
    
    /**
     * Creates a circular reveal animation
     */
    fun createCircularReveal(view: View, centerX: Int, centerY: Int, startRadius: Float, endRadius: Float): Animator {
        val animator = ViewAnimationUtils.createCircularReveal(
            view, centerX, centerY, startRadius, endRadius
        )
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 500
        return animator
    }
    
    /**
     * Creates a full circular reveal animation from center of view
     */
    fun createCircularRevealFromCenter(view: View, show: Boolean): Animator {
        val centerX = view.width / 2
        val centerY = view.height / 2
        
        val startRadius = if (show) 0f else getMaxRadius(view)
        val endRadius = if (show) getMaxRadius(view) else 0f
        
        return createCircularReveal(view, centerX, centerY, startRadius, endRadius)
    }
    
    /**
     * Calculates the maximum radius for a circular reveal
     */
    private fun getMaxRadius(view: View): Float {
        return hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
    }
    
    /**
     * Changes the animation in a LottieAnimationView with a crossfade
     */
    fun changeLottieAnimation(lottieView: LottieAnimationView, animationRes: Int, duration: Long = 300) {
        // Fade out current animation
        lottieView.animate()
            .alpha(0f)
            .setDuration(duration / 2)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Change animation
                    lottieView.setAnimation(animationRes)
                    lottieView.playAnimation()
                    
                    // Fade in new animation
                    lottieView.animate()
                        .alpha(1f)
                        .setDuration(duration / 2)
                        .setListener(null)
                }
            })
    }
} 