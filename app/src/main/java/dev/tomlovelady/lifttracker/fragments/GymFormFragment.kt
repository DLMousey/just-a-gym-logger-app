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
import androidx.navigation.findNavController
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.repositories.GymRepository

/**
 * A simple [Fragment] subclass.
 * Use the [GymFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GymFormFragment : Fragment() {

    private lateinit var editNameView: EditText
    private lateinit var editAddressView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            if (emptyName || emptyAddress) {
                Toast.makeText(context, "Name and address are required", Toast.LENGTH_SHORT).show()
            } else {
                val name = editNameView.text.toString()
                val address = editAddressView.text.toString()

                val bundle = bundleOf("newGym" to "$name|$address")
                view.findNavController().navigate(R.id.gym_form_to_list_create, bundle)
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GymFormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GymFormFragment()
    }
}