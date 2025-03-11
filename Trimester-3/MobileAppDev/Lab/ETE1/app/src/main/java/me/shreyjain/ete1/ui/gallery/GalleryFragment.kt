package me.shreyjain.ete1.ui.gallery

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import me.shreyjain.ete1.R
import me.shreyjain.ete1.data.ImageStorageManager
import me.shreyjain.ete1.databinding.DialogImageUploadBinding
import me.shreyjain.ete1.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var imageStorageManager: ImageStorageManager
    private val imageUris = mutableListOf<Uri>()
    
    // Selected image for category dialog
    private var pendingImageUri: Uri? = null
    private var activeCategory: String? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // Save the URI to use when dialog is confirmed
                pendingImageUri = uri
                showCategoryDialog(uri)
            }
            
            // Handle multiple images if available
            val clipData = result.data?.clipData
            if (clipData != null) {
                pendingImageUri = clipData.getItemAt(0).uri // Use first image as the pending one
                val uris = (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }
                showCategoryDialog(uris)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        imageStorageManager = ImageStorageManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        setupBrightnessControl()
        setupCategoryChips()
        loadImagesFromStorage()
    }
    
    private fun setupCategoryChips() {
        val chipGroup = ChipGroup(requireContext()).apply {
            isSingleSelection = true
            isSelectionRequired = false
        }
        
        // Add "All" chip
        val allChip = Chip(requireContext()).apply {
            text = "All"
            isCheckable = true
            isChecked = true
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    activeCategory = null
                    loadImagesFromStorage()
                }
            }
        }
        chipGroup.addView(allChip)
        
        // Add category chips
        listOf("Highlights", "Cultural Fest", "Technical Symposium", "Innovation Summit").forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        activeCategory = category
                        loadImagesByCategory(category)
                    }
                }
            }
            chipGroup.addView(chip)
        }
        
        // Add the ChipGroup to the layout
        binding.brightnessControlLayout.addView(chipGroup)
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter { imageUri ->
            // Open fullscreen image when grid item is clicked
            openFullscreenImage(imageUri)
        }
        
        binding.galleryRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2) // 2 columns grid
            adapter = galleryAdapter
        }
    }
    
    private fun setupBrightnessControl() {
        binding.brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Calculate brightness value (from -1.0f to 1.0f) from progress (0-100)
                val brightness = (progress - 50) / 50.0f
                applyBrightnessFilter(brightness)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }
        })
    }
    
    private fun applyBrightnessFilter(brightness: Float) {
        // Create a ColorMatrix to adjust brightness
        val colorMatrix = ColorMatrix()
        colorMatrix.set(floatArrayOf(
            1f, 0f, 0f, 0f, brightness * 255, // Red
            0f, 1f, 0f, 0f, brightness * 255, // Green
            0f, 0f, 1f, 0f, brightness * 255, // Blue
            0f, 0f, 0f, 1f, 0f               // Alpha
        ))
        
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        galleryAdapter.setBrightnessFilter(colorFilter)
    }
    
    private fun openFullscreenImage(imageUri: Uri) {
        val allImages = galleryAdapter.currentList
        val bundle = bundleOf(
            FullscreenImageFragment.ARG_IMAGE_URI to imageUri.toString(),
            FullscreenImageFragment.ARG_ALL_IMAGE_URIS to ArrayList(allImages.map { it.toString() })
        )
        findNavController().navigate(R.id.action_gallery_to_fullscreen, bundle)
    }

    private fun setupClickListeners() {
        binding.addImageButton.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        
        try {
            pickImageLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open image picker: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showCategoryDialog(uri: Uri) {
        showCategoryDialog(listOf(uri))
    }
    
    private fun showCategoryDialog(uris: List<Uri>) {
        val dialogBinding = DialogImageUploadBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Save Images")
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
        
        dialogBinding.saveButton.setOnClickListener {
            // Get selected categories
            val categories = mutableListOf<String>()
            if (dialogBinding.checkboxHighlights.isChecked) categories.add("Highlights")
            if (dialogBinding.checkboxCulturalFest.isChecked) categories.add("Cultural Fest")
            if (dialogBinding.checkboxTechnicalSymposium.isChecked) categories.add("Technical Symposium")
            if (dialogBinding.checkboxInnovationSummit.isChecked) categories.add("Innovation Summit")
            
            // Save all images with categories
            uris.forEach { uri ->
                saveImageToStorage(uri, categories)
            }
            
            dialog.dismiss()
        }
        
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }

    private fun saveImageToStorage(sourceUri: Uri, categories: List<String> = emptyList()) {
        // Try to take persistent permissions if it's a content URI
        try {
            if (sourceUri.scheme == "content") {
                requireContext().contentResolver.takePersistableUriPermission(
                    sourceUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        } catch (e: Exception) {
            // Ignore permission errors, we'll still try to copy the file
        }
        
        // Save to storage and update UI if successful
        val savedUri = imageStorageManager.saveImageToStorage(sourceUri, categories)
        savedUri?.let {
            imageUris.add(it)
            updateGalleryView()
            
            // Show success message
            Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImagesFromStorage() {
        // Clear current list
        imageUris.clear()
        
        // Load all saved images
        imageUris.addAll(imageStorageManager.loadAllImages())
        
        // Update UI
        updateGalleryView()
    }
    
    private fun loadImagesByCategory(category: String) {
        // Clear current list
        imageUris.clear()
        
        // Load images filtered by category
        imageUris.addAll(imageStorageManager.loadImagesByCategory(category))
        
        // Update UI
        updateGalleryView()
    }

    private fun updateGalleryView() {
        if (imageUris.isEmpty()) {
            binding.emptyGalleryText.visibility = View.VISIBLE
            binding.galleryRecyclerView.visibility = View.GONE
        } else {
            binding.emptyGalleryText.visibility = View.GONE
            binding.galleryRecyclerView.visibility = View.VISIBLE
            galleryAdapter.submitList(imageUris.toList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 