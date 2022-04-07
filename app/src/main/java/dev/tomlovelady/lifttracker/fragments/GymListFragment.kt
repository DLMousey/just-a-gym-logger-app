package dev.tomlovelady.lifttracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.adapters.GymListAdapter
import dev.tomlovelady.lifttracker.viewmodels.GymViewModel
import dev.tomlovelady.lifttracker.viewmodels.GymViewModelFactory

class GymListFragment : Fragment() {

    private val gymViewModel: GymViewModel by viewModels {
        GymViewModelFactory((activity?.application as LiftTrackerApplication).gymRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gym_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = GymListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        gymViewModel.allGyms.observe(viewLifecycleOwner, Observer { gyms ->
            gyms?.let { adapter.submitList(it) }
        })

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            view.findNavController().navigate(R.id.gym_list_to_form_create)
        }

        return view
    }
}