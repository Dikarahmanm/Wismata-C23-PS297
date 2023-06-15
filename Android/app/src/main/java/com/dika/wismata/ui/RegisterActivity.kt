package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dika.wismata.R
import com.dika.wismata.databinding.ActivityRegisterBinding
import com.dika.wismata.viewmodel.RegisterViewModel
import com.dika.wismata.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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
        doRegister()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun enableButton(status: Boolean) {
        binding.btnRegister.isEnabled = status
    }

    private fun doLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.text = ""
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.text = getString(R.string.Register)
        }
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

    private fun doRegister() {
        binding.btnRegister.setOnClickListener {
            val fistName = binding.editTextFirstName.text.toString().trim()
            val lastName = binding.editTextLastName.text.toString().trim()
            val email = binding.editTextEmailAddress.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if (fistName.isEmpty() && lastName.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                binding.editTextFirstName.error = getString(R.string.name_empty)
                binding.editTextLastName.error = getString(R.string.name_empty)
                binding.editTextEmailAddress.error = getString(R.string.email_empty)
                binding.editTextPassword.error = getString(R.string.password_empty)
                binding.editTextConfirmPassword.error = getString(R.string.confirm_password_empty)
            } else {
                if (password != confirmPassword) {
                    binding.editTextPassword.error = getString(R.string.password_not_match)
                    binding.editTextConfirmPassword.error = getString(R.string.password_not_match)
                } else {
                    doLoading(true)
                    viewModel.doRegister(email, fistName, lastName, password, "traveller")
                    viewModel.message.observe(this) { message ->
                        if (message == "CREATE new user success") {
                            doLoading(false)
                            Toast.makeText(
                                this,
                                getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        } else {
                            doLoading(false)
                            Toast.makeText(
                                this,
                                getString(R.string.register_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, RegisterActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }

        }
    }
}