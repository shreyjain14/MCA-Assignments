package me.shreyjain.ete1.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import me.shreyjain.ete1.data.DatabaseHelper
import me.shreyjain.ete1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())

        // Pre-fill the current username
        binding.usernameInput.setText(dbHelper.getCurrentUsername())
        
        // Setup the theme toggle
        setupThemeToggle()

        binding.updateProfileButton.setOnClickListener {
            val currentUsername = dbHelper.getCurrentUsername() ?: return@setOnClickListener
            val currentPassword = binding.currentPasswordInput.text.toString()
            val newUsername = binding.usernameInput.text.toString()
            val newPassword = binding.newPasswordInput.text.toString()
            val confirmNewPassword = binding.confirmNewPasswordInput.text.toString()

            if (currentPassword.isBlank()) {
                Toast.makeText(context, "Please enter your current password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate current password
            if (!dbHelper.checkUser(currentUsername, currentPassword)) {
                Toast.makeText(context, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if new password fields match
            if (newPassword.isNotBlank() || confirmNewPassword.isNotBlank()) {
                if (newPassword != confirmNewPassword) {
                    Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Update credentials
            val updatedUsername = if (newUsername != currentUsername) newUsername else null
            val updatedPassword = if (newPassword.isNotBlank()) newPassword else null

            if (updatedUsername == null && updatedPassword == null) {
                Toast.makeText(context, "No changes to update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.updateCredentials(currentUsername, currentPassword, updatedUsername, updatedPassword)) {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                // Clear password fields
                binding.currentPasswordInput.text?.clear()
                binding.newPasswordInput.text?.clear()
                binding.confirmNewPasswordInput.text?.clear()
            } else {
                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupThemeToggle() {
        // Get the saved theme mode
        val sharedPrefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        
        // Set the initial toggle state
        binding.themeModeToggle.isChecked = isDarkMode
        
        // Set up listener for toggle changes
        binding.themeModeToggle.setOnCheckedChangeListener { _, isChecked ->
            // Save the preference
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
            
            // Apply the theme
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 