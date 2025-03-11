package me.shreyjain.ete1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import me.shreyjain.ete1.databinding.ActivityMainBinding
import me.shreyjain.ete1.databinding.CustomAppBarBinding
import me.shreyjain.ete1.util.AnimationUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var appBarTitle: TextView
    private var currentDestinationId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Apply theme before setting content view
        applyTheme()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize AppBar components
        initializeAppBar()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom navigation and app bar on auth screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val previousDestinationId = currentDestinationId
            currentDestinationId = destination.id
            
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                    binding.customAppBar.root.visibility = View.GONE
                }
                R.id.fullscreenImageFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                    binding.customAppBar.root.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    binding.customAppBar.root.visibility = View.VISIBLE
                    
                    // Only animate if coming from a different destination
                    if (previousDestinationId != destination.id) {
                        updateAppBar(destination.id)
                    }
                }
            }
        }
    }
    
    private fun initializeAppBar() {
        // Access the views directly through the binding instead of using findViewById
        lottieAnimationView = binding.customAppBar.lottieAnimationView
        appBarTitle = binding.customAppBar.appBarTitle
        
        // Set default animation
        lottieAnimationView.setAnimation(R.raw.loading_animation)
        lottieAnimationView.playAnimation()
    }
    
    private fun updateAppBar(destinationId: Int) {
        // Change the animation and title based on the destination
        when (destinationId) {
            R.id.navigation_home -> {
                appBarTitle.text = "REVELATIONS HOME"
                playTransitionAnimation(R.raw.pulse_animation)
            }
            R.id.navigation_events -> {
                appBarTitle.text = "EVENTS"
                playTransitionAnimation(R.raw.loading_animation)
            }
            R.id.navigation_gallery -> {
                appBarTitle.text = "GALLERY"
                playTransitionAnimation(R.raw.pulse_animation)
            }
            R.id.navigation_social -> {
                appBarTitle.text = "SOCIAL"
                playTransitionAnimation(R.raw.loading_animation)
            }
            R.id.navigation_profile -> {
                appBarTitle.text = "PROFILE"
                playTransitionAnimation(R.raw.pulse_animation)
            }
        }
    }
    
    private fun playTransitionAnimation(animationRes: Int) {
        // Use the smooth transition animation from our utility class
        AnimationUtils.changeLottieAnimation(lottieAnimationView, animationRes)
    }
    
    private fun applyTheme() {
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}