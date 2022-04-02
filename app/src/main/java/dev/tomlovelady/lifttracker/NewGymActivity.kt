package dev.tomlovelady.lifttracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewGymActivity : AppCompatActivity() {

    private lateinit var editNameView: EditText
    private lateinit var editAddressView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_gym)

        editNameView = findViewById(R.id.edit_name)
        editAddressView = findViewById(R.id.edit_address)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            val emptyName = TextUtils.isEmpty(editNameView.text)
            val emptyAddress = TextUtils.isEmpty(editAddressView.text)

            if (emptyName || emptyAddress) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editNameView.text.toString()
                val address = editAddressView.text.toString()

                val combination = name + "|" + address
                replyIntent.putExtra(EXTRA_REPLY, combination)
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "dev.tomlovelady.lifttracker.REPLY"
    }
}