package me.shreyjain.ete1.data

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageStorageManager(private val context: Context) {
    
    private val appImagesDir: File by lazy {
        File(context.filesDir, "gallery_images").apply {
            if (!exists()) {
                mkdir()
            }
        }
    }
    
    // SharedPreferences to store image categories
    private val categoryPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("image_categories", Context.MODE_PRIVATE)
    }
    
    /**
     * Copies an image from a URI to the app's private storage directory with category information
     * @return The Uri of the saved file or null if failed
     */
    fun saveImageToStorage(sourceUri: Uri, categories: List<String> = emptyList()): Uri? {
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(sourceUri)
            
            // Create a unique filename based on timestamp
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFileName = "REVELATIONS_$timeStamp.jpg"
            val destFile = File(appImagesDir, imageFileName)
            
            // Copy the image to our app's storage
            FileOutputStream(destFile).use { outputStream ->
                inputStream?.let {
                    val buffer = ByteArray(4 * 1024) // 4k buffer
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                }
            }
            
            inputStream?.close()
            
            // If we have categories, save them
            if (categories.isNotEmpty()) {
                saveCategoriesForImage(destFile.name, categories)
            }
            
            // Return the URI to the saved file
            return Uri.fromFile(destFile)
            
        } catch (e: IOException) {
            Log.e("ImageStorageManager", "Error saving image: ${e.message}")
            return null
        }
    }
    
    /**
     * Save image categories to SharedPreferences
     */
    private fun saveCategoriesForImage(filename: String, categories: List<String>) {
        categoryPrefs.edit().apply {
            // Store as comma-separated values
            putString(filename, categories.joinToString(","))
            apply()
        }
    }
    
    /**
     * Get categories for a specific image
     */
    fun getCategoriesForImage(imageUri: Uri): List<String> {
        val filename = getFilenameFromUri(imageUri)
        val categoriesString = categoryPrefs.getString(filename, "")
        return if (categoriesString.isNullOrEmpty()) {
            emptyList()
        } else {
            categoriesString.split(",")
        }
    }
    
    /**
     * Extract filename from Uri
     */
    private fun getFilenameFromUri(uri: Uri): String {
        return uri.path?.let { path ->
            val lastSlashIndex = path.lastIndexOf('/')
            if (lastSlashIndex >= 0 && lastSlashIndex < path.length - 1) {
                path.substring(lastSlashIndex + 1)
            } else {
                path // Use the full path if no slash found
            }
        } ?: ""
    }
    
    /**
     * Loads all saved images from storage
     * @return List of URIs for all images in the gallery
     */
    fun loadAllImages(): List<Uri> {
        val imageUris = mutableListOf<Uri>()
        
        if (appImagesDir.exists()) {
            appImagesDir.listFiles()?.forEach { file ->
                if (file.isFile && isImageFile(file.name)) {
                    imageUris.add(Uri.fromFile(file))
                }
            }
        }
        
        return imageUris
    }
    
    /**
     * Loads images filtered by category
     */
    fun loadImagesByCategory(category: String): List<Uri> {
        return loadAllImages().filter { uri ->
            getCategoriesForImage(uri).contains(category)
        }
    }
    
    /**
     * Simple check to see if the file has an image extension
     */
    private fun isImageFile(filename: String): Boolean {
        val extensions = listOf("jpg", "jpeg", "png", "gif", "webp")
        return extensions.any { filename.lowercase().endsWith(it) }
    }
    
    /**
     * Deletes a specific image
     */
    fun deleteImage(imageUri: Uri): Boolean {
        try {
            val file = File(imageUri.path ?: return false)
            val filename = file.name
            
            // Remove the category entries
            categoryPrefs.edit().remove(filename).apply()
            
            return file.delete()
        } catch (e: Exception) {
            Log.e("ImageStorageManager", "Error deleting image: ${e.message}")
            return false
        }
    }
    
    /**
     * Clear all images from storage
     */
    fun clearAllImages(): Boolean {
        return try {
            // Clear categories
            categoryPrefs.edit().clear().apply()
            
            // Delete files
            appImagesDir.listFiles()?.forEach { it.delete() }
            true
        } catch (e: Exception) {
            Log.e("ImageStorageManager", "Error clearing images: ${e.message}")
            false
        }
    }
} 