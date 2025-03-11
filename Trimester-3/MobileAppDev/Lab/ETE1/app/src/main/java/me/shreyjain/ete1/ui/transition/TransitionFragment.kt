package me.shreyjain.ete1.ui.transition

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import me.shreyjain.ete1.R
import me.shreyjain.ete1.databinding.FragmentTransitionBinding

/**
 * A fragment that plays a transition animation between other fragments
 */
class TransitionFragment : Fragment() {
    private var _binding: FragmentTransitionBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var animationView: LottieAnimationView
    private var onTransitionEndCallback: (() -> Unit)? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransitionBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        animationView = binding.transitionAnimation
        
        // Get the animation resource ID from arguments
        arguments?.getInt(ARG_ANIMATION_RES)?.let { animRes ->
            if (animRes != 0) {
                animationView.setAnimation(animRes)
            }
        }
        
        // Get the transition duration from arguments
        val duration = arguments?.getLong(ARG_DURATION) ?: DEFAULT_DURATION
        
        // Start the animation
        animationView.playAnimation()
        
        // Schedule the end callback
        Handler(Looper.getMainLooper()).postDelayed({
            onTransitionEndCallback?.invoke()
        }, duration)
    }
    
    fun setOnTransitionEndListener(callback: () -> Unit) {
        onTransitionEndCallback = callback
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        private const val ARG_ANIMATION_RES = "animation_res"
        private const val ARG_DURATION = "duration"
        private const val DEFAULT_DURATION = 1000L
        
        fun newInstance(animationResId: Int = R.raw.transition_animation, duration: Long = DEFAULT_DURATION): TransitionFragment {
            return TransitionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ANIMATION_RES, animationResId)
                    putLong(ARG_DURATION, duration)
                }
            }
        }
    }
} 