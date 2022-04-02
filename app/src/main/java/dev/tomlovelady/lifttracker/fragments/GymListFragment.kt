package dev.tomlovelady.lifttracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.adapters.GymListAdapter
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.viewmodels.GymViewModel
import dev.tomlovelady.lifttracker.viewmodels.GymViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [GymListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GymListFragment : Fragment() {

//    private val args by navArgs<GymListFragmentArgs>()

    private val newGymActivityRequestCode = 1
    private val gymViewModel: GymViewModel by viewModels {
        GymViewModelFactory((activity?.application as LiftTrackerApplication).gymRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newGym = arguments?.getString("newGym") ?: return

        val parts = newGym.split("|")
        val gym: Gym = Gym(0, parts[0], parts[1])

        gymViewModel.insert(gym)

        Toast.makeText(context, "'${gym.name}' saved successfully", Toast.LENGTH_SHORT).show()
        Log.d("GymListFragment:onViewCreated", "received argument 'newGym' with value: " + newGym)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GymListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GymListFragment()
    }
}