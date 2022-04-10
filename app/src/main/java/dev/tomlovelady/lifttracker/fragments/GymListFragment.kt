package dev.tomlovelady.lifttracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.LiftTrackerDatabase
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.adapters.GymListAdapter
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.viewmodels.GymViewModel
import dev.tomlovelady.lifttracker.viewmodels.GymViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GymListFragment : Fragment() {

    private val gymViewModel: GymViewModel by viewModels {
        GymViewModelFactory((activity?.application as LiftTrackerApplication).gymRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Gyms List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gym_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = GymListAdapter()
        adapter.database = LiftTrackerDatabase.getDatabase(view.context, CoroutineScope(
            SupervisorJob()
        )
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        gymViewModel.allGyms.observe(viewLifecycleOwner, Observer { gyms ->
            gyms?.let { adapter.submitList(it) }
        })

        val formFragment = GymFormFragment()

        val fab = view.findViewById<Button>(R.id.gymList_add_gym_button)
        fab.setOnClickListener {
            formFragment.show(childFragmentManager, "TAG")
            formFragment.setFragmentResultListener("newGym") { requestKey, bundle ->
                val string = bundle.getString("gym")
                val parts = string!!.split("|")
                val gym = Gym(parts[0], parts[1], parts[2])
                gymViewModel.insert(gym)
            }
        }

        return view
    }
}