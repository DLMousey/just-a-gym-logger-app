package dev.tomlovelady.lifttracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.adapters.SessionListAdapter
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.entities.Session
import dev.tomlovelady.lifttracker.viewmodels.SessionViewModel
import dev.tomlovelady.lifttracker.viewmodels.SessionViewModelFactory

class SessionListFragment : Fragment() {

    private lateinit var gym: Gym

    private val args: SessionListFragmentArgs by navArgs()

    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory((activity?.application as LiftTrackerApplication).sessionRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Gym Sessions List"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_session_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = (activity?.application as LiftTrackerApplication)

        application.gymRepository.findGymById(args.sessionListGymId).asLiveData().observe(
            viewLifecycleOwner, Observer<Gym> {
                gym = it
            }
        )

        val emptyText = view.findViewById<TextView>(R.id.textview_sessionList_empty)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_sessionList)

        application.sessionRepository.countSessionsByGym(args.sessionListGymId)
            .asLiveData()
            .observe(
            viewLifecycleOwner
        ) {
            when (it) {
                0 -> {
                    Log.d("SessionListFragment", "onViewCreated: Session count change to 0, hiding list, showing empty")
                    recyclerView.visibility = View.GONE
                    emptyText.visibility = View.VISIBLE
                }
                else -> {
                    Log.d("SessionListFragment", "onViewCreated: Session count changed to ${it}, showing list, hiding empty")
                    recyclerView.visibility = View.VISIBLE
                    emptyText.visibility = View.GONE
                }
            }
        }

        val adapter = SessionListAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        sessionViewModel.loadSessionsForGym(args.sessionListGymId)
        sessionViewModel.currentSessions.observe(viewLifecycleOwner, Observer<List<Session>> {
            sessionList -> sessionList?.let { adapter.submitList(it) }
        })

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_sessionList)
        fab.setOnClickListener {
            val now: Long = System.currentTimeMillis() / 1000L
            val session = Session(0, args.sessionListGymId, now, null)

            sessionViewModel.insert(session)
            Toast.makeText(context, "Created a new session at ${gym.name}", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SessionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SessionListFragment()
    }
}