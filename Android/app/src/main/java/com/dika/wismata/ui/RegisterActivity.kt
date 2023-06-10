package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.graphics.drawable.toDrawable
import com.dika.wismata.R
import com.dika.wismata.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.Register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        customComponent()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun enableButton(status: Boolean) {
        binding.btnRegister.isEnabled = status
    }

    private fun customComponent() {

        binding.editTextFirstName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {

                val firstName = binding.editTextFirstName.text.toString().trim()
                val lastName = binding.editTextLastName.text.toString().trim()
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })

        binding.editTextLastName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {

                val firstName = binding.editTextFirstName.text.toString().trim()
                val lastName = binding.editTextLastName.text.toString().trim()
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })

        binding.editTextEmailAddress.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {

                val firstName = binding.editTextFirstName.text.toString().trim()
                val lastName = binding.editTextLastName.text.toString().trim()
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
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

                val firstName = binding.editTextFirstName.text.toString().trim()
                val lastName = binding.editTextLastName.text.toString().trim()
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })

        binding.editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {

                val firstName = binding.editTextFirstName.text.toString().trim()
                val lastName = binding.editTextLastName.text.toString().trim()
                val email = binding.editTextEmailAddress.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()
                val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    enableButton(true)
                } else {
                    enableButton(false)
                }
            }
        })
    }
}