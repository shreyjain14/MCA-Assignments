package me.shreyjain.ete1.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.shreyjain.ete1.databinding.ItemCarouselImageBinding

class ImageCarouselAdapter(private val onImageClick: (Uri) -> Unit) : 
    ListAdapter<Uri, ImageCarouselAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemCarouselImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding, onImageClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ImageViewHolder(
        private val binding: ItemCarouselImageBinding,
        private val onImageClick: (Uri) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(imageUri: Uri) {
            binding.carouselImage.setImageURI(imageUri)
            binding.root.setOnClickListener {
                onImageClick(imageUri)
            }
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