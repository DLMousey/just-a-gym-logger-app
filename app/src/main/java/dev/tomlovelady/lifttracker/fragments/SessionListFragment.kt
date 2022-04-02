package dev.tomlovelady.lifttracker.fragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.adapters.SessionListAdapter
import dev.tomlovelady.lifttracker.entities.Session
import dev.tomlovelady.lifttracker.viewmodels.SessionViewModel
import dev.tomlovelady.lifttracker.viewmodels.SessionViewModelFactory
import kotlinx.coroutines.flow.count

class SessionListFragment : Fragment() {

    private val args: SessionListFragmentArgs by navArgs()

    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory((activity?.application as LiftTrackerApplication).sessionRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_session_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repository = (activity?.application as LiftTrackerApplication)
            .sessionRepository

        val count = repository.countSessionsByGym(args.sessionListGymId).asLiveData().value
        if (count == 0 || count == null) {
            view.findViewById<TextView>(R.id.textview_sessionList_empty).visibility = View.VISIBLE
            return
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_sessionList)
        val adapter = SessionListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val sessions = (activity?.application as LiftTrackerApplication)
            .sessionRepository.findSessionsByGym(args.sessionListGymId)

        sessionViewModel.allSessions = sessions.asLiveData()
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