package dev.tomlovelady.lifttracker.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.repositories.GymRepository
import dev.tomlovelady.lifttracker.viewmodels.GymViewModel
import dev.tomlovelady.lifttracker.viewmodels.GymViewModelFactory

class GymFormFragment : Fragment() {

    private lateinit var editNameView: EditText
    private lateinit var editAddressView: EditText

    private val gymViewModel: GymViewModel by viewModels {
        GymViewModelFactory((activity?.application as LiftTrackerApplication).gymRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gym_form, container, false)

        editNameView = view.findViewById(R.id.edit_name)
        editAddressView = view.findViewById(R.id.edit_address)

        val button = view.findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val emptyName = TextUtils.isEmpty(editNameView.text)
            val emptyAddress = TextUtils.isEmpty(editAddressView.text)

            val repo = (activity?.application as LiftTrackerApplication).gymRepository

            if (emptyName || emptyAddress) {
                Toast.makeText(context, "Name and address are required", Toast.LENGTH_SHORT).show()
            } else {
                val name = editNameView.text.toString()
                val address = editAddressView.text.toString()

                val gym = Gym(0, name, address)
                gymViewModel.insert(gym)

                Toast.makeText(context, "New gym: '${name}' added succesfully", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.gym_form_to_list_create)
            }
        }

        return view
    }
}