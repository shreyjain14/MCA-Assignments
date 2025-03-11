package me.shreyjain.ete1.ui.events

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.shreyjain.ete1.databinding.ItemEventBinding

class EventsAdapter(private val context: Context) : ListAdapter<Event, EventsAdapter.EventViewHolder>(EventDiffCallback()) {
    
    private val ratingsPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("event_ratings", Context.MODE_PRIVATE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        
        // Load saved ratings for this event
        loadEventRatings(event)
        
        holder.bind(event)
    }
    
    private fun loadEventRatings(event: Event) {
        // Get saved ratings count
        val ratingsKey = "event_${event.id}_ratings"
        val ratingsCount = ratingsPrefs.getInt(ratingsKey, 0)
        
        // Get saved ratings total
        val totalKey = "event_${event.id}_total"
        val ratingsTotal = ratingsPrefs.getFloat(totalKey, 0f)
        
        // Calculate average
        if (ratingsCount > 0) {
            event.totalRatings = ratingsCount
            event.averageRating = ratingsTotal / ratingsCount
        }
    }
    
    private fun saveEventRating(event: Event, newRating: Float) {
        // Calculate new values
        val newTotal = event.averageRating * event.totalRatings + newRating
        val newCount = event.totalRatings + 1
        val newAverage = newTotal / newCount
        
        // Update the event object
        event.totalRatings = newCount
        event.averageRating = newAverage
        
        // Save to preferences
        ratingsPrefs.edit().apply {
            putInt("event_${event.id}_ratings", newCount)
            putFloat("event_${event.id}_total", newTotal)
            apply()
        }
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.apply {
                eventImage.setImageResource(event.imageResId)
                eventTitle.text = event.title
                eventDescription.text = event.description
                eventDateTime.text = "${event.date} at ${event.time}"
                eventVenue.text = event.venue
                
                // Set up rating display
                updateRatingDisplay(event)
                
                // Set up rating change listener
                eventRatingBar.onRatingBarChangeListener = 
                    RatingBar.OnRatingBarChangeListener { _, rating, fromUser ->
                        if (fromUser) {
                            saveEventRating(event, rating)
                            updateRatingDisplay(event)
                        }
                    }
                
                // Register button click listener
                registerButton.setOnClickListener {
                    // TODO: Implement registration logic
                }
                
                // Share button click listener
                shareButton.setOnClickListener {
                    // TODO: Implement share functionality
                }
            }
        }
        
        private fun updateRatingDisplay(event: Event) {
            binding.apply {
                // Set the RatingBar to show the average
                eventRatingBar.rating = event.averageRating
                
                // Update the text to show the average and count
                val formattedAverage = String.format("%.1f", event.averageRating)
                eventRatingText.text = "Average rating: $formattedAverage (${event.totalRatings} ratings)"
            }
        }
    }

    private class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
} 