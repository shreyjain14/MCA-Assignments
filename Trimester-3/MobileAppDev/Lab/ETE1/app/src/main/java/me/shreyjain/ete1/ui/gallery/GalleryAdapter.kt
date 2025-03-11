package me.shreyjain.ete1.ui.gallery

import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.shreyjain.ete1.databinding.ItemGalleryImageBinding

class GalleryAdapter(private val onImageClick: (Uri) -> Unit = {}) : 
    ListAdapter<Uri, GalleryAdapter.ImageViewHolder>(ImageDiffCallback()) {

    private var brightnessFilter: ColorMatrixColorFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemGalleryImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = getItem(position)
        holder.bind(uri, brightnessFilter)
        holder.itemView.setOnClickListener {
            onImageClick(uri)
        }
    }
    
    fun setBrightnessFilter(filter: ColorMatrixColorFilter?) {
        brightnessFilter = filter
        notifyDataSetChanged() // Update all views with the new filter
    }

    class ImageViewHolder(private val binding: ItemGalleryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUri: Uri, brightnessFilter: ColorMatrixColorFilter?) {
            binding.galleryImage.setImageURI(imageUri)
            
            // Apply brightness filter if available
            binding.galleryImage.colorFilter = brightnessFilter
        }
    }

    private class ImageDiffCallback : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }
} 