package me.shreyjain.ete1.ui.gallery

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import me.shreyjain.ete1.databinding.FragmentFullscreenImageBinding

class FullscreenImageFragment : Fragment() {
    private var _binding: FragmentFullscreenImageBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: FullscreenImageAdapter
    private var imageUris = listOf<Uri>()
    private var startPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullscreenImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        try {
            // Hide system UI for immersive fullscreen experience
            setupFullscreenMode()
            
            // Process arguments
            processArguments()
            
            // Set up close button
            binding.closeButton.setOnClickListener {
                findNavController().navigateUp()
            }
        } catch (e: Exception) {
            // Handle any unexpected errors
            Toast.makeText(context, "Error loading images: ${e.message}", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp() // Go back if there's an error
        }
    }
    
    private fun processArguments() {
        try {
            // Get the main image URI string
            val mainImageUriString = arguments?.getString(ARG_IMAGE_URI)
            if (mainImageUriString.isNullOrEmpty()) {
                Toast.makeText(context, "No image to display", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
                return
            }
            
            // Get all image URIs if available
            val allImageUriStrings = arguments?.getStringArrayList(ARG_ALL_IMAGE_URIS)
            
            if (allImageUriStrings != null && allImageUriStrings.isNotEmpty()) {
                // Use the provided list of images
                imageUris = allImageUriStrings.mapNotNull { 
                    try {
                        Uri.parse(it)
                    } catch (e: Exception) {
                        null // Skip invalid URIs
                    }
                }
                
                // Find the position of the main image in the list
                val mainUri = Uri.parse(mainImageUriString)
                startPosition = imageUris.indexOfFirst { it.toString() == mainImageUriString }
                if (startPosition == -1) startPosition = 0
            } else {
                // If no list was provided, just use the single image
                imageUris = listOf(Uri.parse(mainImageUriString))
                startPosition = 0
            }
            
            // Only proceed if we have images
            if (imageUris.isNotEmpty()) {
                setupViewPager()
            } else {
                Toast.makeText(context, "No valid images to display", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error processing images: ${e.message}", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
    
    private fun setupViewPager() {
        try {
            // Initialize adapter with our images
            adapter = FullscreenImageAdapter(imageUris)
            
            // Setup ViewPager2
            binding.fullscreenPager.apply {
                adapter = this@FullscreenImageFragment.adapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                
                // Update page indicator when page changes
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        updatePageIndicator(position)
                    }
                })
            }
            
            // Set the initial position (safely)
            if (startPosition in 0 until imageUris.size) {
                binding.fullscreenPager.setCurrentItem(startPosition, false)
                updatePageIndicator(startPosition)
            } else if (imageUris.isNotEmpty()) {
                binding.fullscreenPager.setCurrentItem(0, false)
                updatePageIndicator(0)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error setting up image viewer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun updatePageIndicator(position: Int) {
        binding.pageIndicator.text = "${position + 1} / ${imageUris.size}"
    }
    
    private fun setupFullscreenMode() {
        try {
            val decorView = requireActivity().window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        } catch (e: Exception) {
            // If we can't enter fullscreen mode, just continue without it
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        try {
            // Restore system UI
            val decorView = requireActivity().window.decorView
            decorView.systemUiVisibility = 0
        } catch (e: Exception) {
            // Ignore any errors when restoring UI
        }
        _binding = null
    }
    
    companion object {
        const val ARG_IMAGE_URI = "image_uri"
        const val ARG_ALL_IMAGE_URIS = "all_image_uris"
        
        fun newInstance(imageUri: Uri, allImageUris: List<Uri>? = null): FullscreenImageFragment {
            return FullscreenImageFragment().apply {
                arguments = bundleOf(
                    ARG_IMAGE_URI to imageUri.toString(),
                    ARG_ALL_IMAGE_URIS to allImageUris?.map { it.toString() }?.toArrayList()
                )
            }
        }
        
        // Extension function to convert List<String> to ArrayList<String>
        private fun List<String>.toArrayList(): ArrayList<String> {
            return ArrayList<String>(this)
        }
    }
} 