package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dika.wismata.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        customComponent()
    }

    private fun enableButton(status: Boolean) {
        binding.btnLogin.isEnabled = status
    }

    private fun customComponent() {
        binding.editTextEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                if (email.isNotEmpty() && email.matches(emailPattern) && password.isNotEmpty() && password.length >= 8) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                if (email.isNotEmpty() && email.matches(emailPattern) && password.isNotEmpty() && password.length >= 8) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })
    }
}