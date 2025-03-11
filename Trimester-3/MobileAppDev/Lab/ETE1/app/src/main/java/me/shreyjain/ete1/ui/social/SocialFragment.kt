package me.shreyjain.ete1.ui.social

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
// Remove imports temporarily
// import com.airbnb.lottie.LottieAnimationView
// import com.airbnb.lottie.LottieDrawable
import me.shreyjain.ete1.R
import me.shreyjain.ete1.databinding.FragmentSocialBinding
import me.shreyjain.ete1.util.AnimationUtils

class SocialFragment : Fragment() {
    private var _binding: FragmentSocialBinding? = null
    private val binding get() = _binding!!
    private var isFirstLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupShareButtons()
        // setupShareAnimation()
        
        // Show share dialog on first navigation to this tab with a short delay
        if (isFirstLoad) {
            Handler(Looper.getMainLooper()).postDelayed({
                showSharePrompt()
                isFirstLoad = false
            }, 1500) // 1.5 second delay
        }
    }
    
    private fun setupShareButtons() {
        // Setup Facebook share
        binding.facebookButton.setOnClickListener {
            shareToSocialMedia("Facebook")
        }
        
        // Setup Twitter share
        binding.twitterButton.setOnClickListener {
            shareToSocialMedia("Twitter")
        }
        
        // Setup Instagram share
        binding.instagramButton.setOnClickListener {
            shareToSocialMedia("Instagram")
        }
        
        // Setup general share button
        binding.shareButton.setOnClickListener {
            showSharePrompt()
        }
    }
    
    // Temporarily comment out
    /*
    private fun setupShareAnimation() {
        // Setup the share animation
        binding.shareAnimation.apply {
            setAnimation(R.raw.share_animation)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }
    */
    
    private fun shareToSocialMedia(platform: String) {
        // Create the share message
        val shareMessage = buildShareMessage(platform)
        
        // Create the share intent
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Join me at Revelations 2025!")
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        
        // Show the share chooser
        startActivity(Intent.createChooser(intent, "Share via"))
    }
    
    private fun showSharePrompt() {
        // Create the share message
        val shareMessage = buildShareMessage()
        
        // Create the share intent
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Join me at Revelations 2025!")
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        
        // Show the share chooser with animation
        val shareIntent = Intent.createChooser(intent, "Spread the word about Revelations 2025!")
        startActivity(shareIntent)
        
        // Play animation effect
        // animateShareEffect()
    }
    
    private fun buildShareMessage(platform: String = ""): String {
        val baseMessage = "I'm excited to be part of Revelations 2025! Join me for this incredible event packed with amazing performances, workshops, and unforgettable experiences. #Revelations2025 #JoinTheExperience"
        
        return when (platform) {
            "Facebook" -> "$baseMessage\nLearn more at: www.revelations2025.com"
            "Twitter" -> "$baseMessage\nFollow @Revelations2025 for updates!"
            "Instagram" -> "$baseMessage\nTag us @Revelations2025 in your posts!"
            else -> baseMessage
        }
    }
    
    // Temporarily comment out
    /*
    private fun animateShareEffect() {
        // Create a circular reveal animation when sharing
        val centerX = binding.shareAnimation.width / 2
        val centerY = binding.shareAnimation.height / 2
        val startRadius = 0f
        val endRadius = Math.hypot(binding.shareAnimation.width.toDouble(), binding.shareAnimation.height.toDouble()).toFloat()
        
        val animator = AnimationUtils.createCircularReveal(
            binding.shareAnimation,
            centerX,
            centerY,
            startRadius,
            endRadius
        )
        
        binding.shareAnimation.visibility = View.VISIBLE
        animator.start()
    }
    */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 