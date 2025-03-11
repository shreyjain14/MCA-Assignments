package me.shreyjain.ete1.ui.gallery

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.shreyjain.ete1.R

class FullscreenImageAdapter(private val imageUris: List<Uri>) : 
    RecyclerView.Adapter<FullscreenImageAdapter.FullscreenImageViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullscreenImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fullscreen_image, parent, false)
        return FullscreenImageViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: FullscreenImageViewHolder, position: Int) {
        try {
            if (position < imageUris.size) {
                holder.bind(imageUris[position])
            }
        } catch (e: Exception) {
            Log.e("FullscreenAdapter", "Error binding image at position $position", e)
        }
    }
    
    override fun getItemCount(): Int = imageUris.size
    
    class FullscreenImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.zoomableImage)
        private lateinit var scaleGestureDetector: ScaleGestureDetector
        
        // Scaling properties
        private var scaleFactor = 1.0f
        private val minScale = 0.5f
        private val maxScale = 5.0f
        
        // Touch properties for dragging
        private var lastTouchX = 0f
        private var lastTouchY = 0f
        private var posX = 0f
        private var posY = 0f
        private var isDragging = false
        
        // For detecting multitouch
        private var pointerCount = 0
        
        init {
            try {
                // Initialize scale gesture detector
                scaleGestureDetector = ScaleGestureDetector(itemView.context, ScaleListener())
                
                // Set touch listener for zoom and drag
                imageView.setOnTouchListener { view, event ->
                    try {
                        // Update pointer count
                        pointerCount = event.pointerCount
                        
                        // Handle scaling
                        scaleGestureDetector.onTouchEvent(event)
                        
                        when (event.actionMasked) {
                            MotionEvent.ACTION_DOWN -> {
                                lastTouchX = event.x
                                lastTouchY = event.y
                                isDragging = true
                                // Allow parent intercepting if not zoomed
                                view.parent?.requestDisallowInterceptTouchEvent(scaleFactor > 1.0f)
                                true
                            }
                            MotionEvent.ACTION_POINTER_DOWN -> {
                                // For multitouch, disable parent intercept
                                view.parent?.requestDisallowInterceptTouchEvent(true)
                                true
                            }
                            MotionEvent.ACTION_MOVE -> {
                                if (pointerCount > 1) {
                                    // Multiple fingers - scaling in progress
                                    view.parent?.requestDisallowInterceptTouchEvent(true)
                                    true
                                } else if (isDragging && scaleFactor > 1.0f) {
                                    // Single finger drag while zoomed in
                                    val dx = event.x - lastTouchX
                                    val dy = event.y - lastTouchY
                                    
                                    // Only allow dragging when zoomed in
                                    posX += dx
                                    posY += dy
                                    applyTransformation()
                                    
                                    // Prevent parent from intercepting touch events when dragging zoomed image
                                    view.parent?.requestDisallowInterceptTouchEvent(true)
                                    
                                    lastTouchX = event.x
                                    lastTouchY = event.y
                                    true
                                } else {
                                    // Not zoomed in, allow swiping
                                    view.parent?.requestDisallowInterceptTouchEvent(false)
                                    false
                                }
                            }
                            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_POINTER_UP -> {
                                pointerCount = event.pointerCount
                                isDragging = false
                                // Reset position if scale is back to normal
                                if (scaleFactor <= 1.0f) {
                                    resetPosition()
                                    // Allow parent to intercept again
                                    view.parent?.requestDisallowInterceptTouchEvent(false)
                                }
                                true
                            }
                            else -> false
                        }
                    } catch (e: Exception) {
                        Log.e("FullscreenAdapter", "Error handling touch event", e)
                        false
                    }
                }
                
                // Double tap to reset zoom
                imageView.setOnClickListener { 
                    try {
                        if (scaleFactor != 1.0f) {
                            resetZoom()
                        }
                    } catch (e: Exception) {
                        Log.e("FullscreenAdapter", "Error handling click", e)
                    }
                }
            } catch (e: Exception) {
                Log.e("FullscreenAdapter", "Error initializing view holder", e)
            }
        }
        
        fun bind(imageUri: Uri) {
            try {
                imageView.setImageURI(imageUri)
                // Handle the case where the image couldn't be loaded
                if (imageView.drawable == null) {
                    // Set a placeholder or error image
                    imageView.setImageResource(android.R.drawable.ic_menu_report_image)
                    Toast.makeText(itemView.context, "Couldn't load image", Toast.LENGTH_SHORT).show()
                }
                resetZoom() // Reset zoom when binding new image
            } catch (e: Exception) {
                Log.e("FullscreenAdapter", "Error binding image $imageUri", e)
                // Set a placeholder for errors
                imageView.setImageResource(android.R.drawable.ic_menu_report_image)
            }
        }
        
        private fun resetZoom() {
            try {
                scaleFactor = 1.0f
                resetPosition()
                applyTransformation()
                // Allow parent to intercept touch events again
                itemView.parent?.requestDisallowInterceptTouchEvent(false)
            } catch (e: Exception) {
                Log.e("FullscreenAdapter", "Error resetting zoom", e)
            }
        }
        
        private fun resetPosition() {
            try {
                posX = 0f
                posY = 0f
                applyTransformation()
            } catch (e: Exception) {
                Log.e("FullscreenAdapter", "Error resetting position", e)
            }
        }
        
        private fun applyTransformation() {
            try {
                imageView.scaleX = scaleFactor
                imageView.scaleY = scaleFactor
                imageView.translationX = posX
                imageView.translationY = posY
            } catch (e: Exception) {
                Log.e("FullscreenAdapter", "Error applying transformation", e)
            }
        }
        
        private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                try {
                    scaleFactor *= detector.scaleFactor
                    
                    // Constrain scale
                    scaleFactor = scaleFactor.coerceIn(minScale, maxScale)
                    
                    applyTransformation()
                    return true
                } catch (e: Exception) {
                    Log.e("FullscreenAdapter", "Error in onScale", e)
                    return false
                }
            }
            
            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                try {
                    // Disable ViewPager2 swiping during scaling
                    itemView.parent?.requestDisallowInterceptTouchEvent(true)
                    return true
                } catch (e: Exception) {
                    Log.e("FullscreenAdapter", "Error in onScaleBegin", e)
                    return false
                }
            }
        }
    }
} 