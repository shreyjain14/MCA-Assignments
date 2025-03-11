package me.shreyjain.ete1.ui.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import me.shreyjain.ete1.R
import me.shreyjain.ete1.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadEvents()
    }

    private fun setupRecyclerView() {
        eventsAdapter = EventsAdapter(requireContext())
        binding.eventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventsAdapter
        }
    }

    private fun loadEvents() {
        // Sample events data
        val events = listOf(
            Event(
                id = "tech_symposium",
                title = "Technical Symposium",
                description = "Showcase your technical skills in coding, robotics, and more!",
                date = "2025-03-15",
                time = "9:00 AM",
                venue = "Main Auditorium",
                imageResId = R.drawable.event_tech
            ),
            Event(
                id = "cultural_night",
                title = "Cultural Night",
                description = "Experience the diversity of cultures through dance, music, and art!",
                date = "2025-03-16",
                time = "6:00 PM",
                venue = "Open Air Theatre",
                imageResId = R.drawable.event_cultural
            ),
            Event(
                id = "innovation_summit",
                title = "Innovation Summit",
                description = "Join industry leaders and innovators in this exciting summit!",
                date = "2025-03-17",
                time = "10:00 AM",
                venue = "Conference Hall",
                imageResId = R.drawable.event_summit
            )
        )
        eventsAdapter.submitList(events)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val venue: String,
    val imageResId: Int,
    var averageRating: Float = 0.0f,
    var totalRatings: Int = 0
) 