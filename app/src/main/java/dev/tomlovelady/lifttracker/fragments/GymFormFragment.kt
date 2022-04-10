package dev.tomlovelady.lifttracker.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import dev.tomlovelady.lifttracker.R

class GymFormFragment : BottomSheetDialogFragment() {

    private lateinit var editNameView: TextInputEditText
    private lateinit var editAddressView: TextInputEditText
    private lateinit var editTypeView: TextInputEditText

    // Apparently calling setContentView on dialog is a restricted API? not a clue.
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_gym_form, null)
        dialog.setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Add a Gym"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gym_form, container, false)

        editNameView = view.findViewById(R.id.edit_name)
        editTypeView = view.findViewById(R.id.edit_type)
        editAddressView = view.findViewById(R.id.edit_address)

        val button = view.findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val emptyName = TextUtils.isEmpty(editNameView.text)
            val emptyAddress = TextUtils.isEmpty(editAddressView.text)
            val emptyType = TextUtils.isEmpty(editTypeView.text)

            if (emptyName || emptyAddress) {
                Toast.makeText(context, "Name and address are required", Toast.LENGTH_SHORT).show()
            } else {
                val name = editNameView.text.toString()
                val type = if (emptyType) "General Gym" else editNameView.text.toString()
                val address = editAddressView.text.toString()

                setFragmentResult("newGym", bundleOf("gym" to "${name}|${type}|${address}"))

                editNameView.text = null
                editTypeView.text = null
                editAddressView.text = null

                dialog?.dismiss()
            }
        }

        return view
    }
}