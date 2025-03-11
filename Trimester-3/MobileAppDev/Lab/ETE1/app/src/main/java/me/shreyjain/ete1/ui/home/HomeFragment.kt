package me.shreyjain.ete1.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import me.shreyjain.ete1.R
import me.shreyjain.ete1.data.ImageStorageManager
import me.shreyjain.ete1.databinding.FragmentHomeBinding
import me.shreyjain.ete1.ui.gallery.FullscreenImageFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var carouselAdapter: ImageCarouselAdapter
    private lateinit var imageStorageManager: ImageStorageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        imageStorageManager = ImageStorageManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCarousel()
        setupButtons()
        loadImages()
    }
    
    private fun setupCarousel() {
        carouselAdapter = ImageCarouselAdapter { imageUri ->
            // Handle image click by opening fullscreen view
            openFullscreenImage(imageUri)
        }
        
        binding.imageCarousel.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = carouselAdapter
            
            // Add snap helper to make it page properly
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
            
            // Add some padding to show multiple items
            clipToPadding = false
            setPadding(50, 0, 50, 0)
        }
    }
    
    private fun openFullscreenImage(imageUri: Uri) {
        val imageUris = carouselAdapter.currentList
        val bundle = bundleOf(
            FullscreenImageFragment.ARG_IMAGE_URI to imageUri.toString(),
            FullscreenImageFragment.ARG_ALL_IMAGE_URIS to ArrayList(imageUris.map { it.toString() })
        )
        findNavController().navigate(R.id.action_home_to_fullscreen, bundle)
    }
    
    private fun setupButtons() {
        binding.exploreEventsButton.setOnClickListener {
            // Navigate to events tab when clicked
            findNavController().navigate(R.id.navigation_events)
        }
    }
    
    private fun loadImages() {
        // Load all saved images
        val imageUris = imageStorageManager.loadAllImages()
        
        if (imageUris.isEmpty()) {
            binding.noImagesText.visibility = View.VISIBLE
            binding.imageCarousel.visibility = View.GONE
        } else {
            binding.noImagesText.visibility = View.GONE
            binding.imageCarousel.visibility = View.VISIBLE
            carouselAdapter.submitList(imageUris)
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Reload images when fragment resumes, in case they've changed
        loadImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 