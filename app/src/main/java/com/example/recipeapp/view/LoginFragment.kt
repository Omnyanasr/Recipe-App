package com.example.recipeapp.view

import AppDatabase
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.authintication.SharedPrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var db: AppDatabase
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        emailEditText = view.findViewById(R.id.etEmail)
        passwordEditText = view.findViewById(R.id.etPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        registerButton = view.findViewById(R.id.registerLink)

        // Initialize database and SharedPrefManager
        db = AppDatabase.getDatabase(requireContext()) // Ensure correct initialization
        sharedPrefManager =  SharedPrefManager.getInstance(requireActivity())

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        // Set up register button click listener
        registerButton.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    // Validate user input
    private fun validateInput(email: String, password: String): Boolean {
        return if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    // Login the user
    private fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = db.userDao().getUser(email, password)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        sharedPrefManager.saveLoginStatus(true)
                        val intent = Intent(requireContext(), RecipeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()  // Prevents back navigation to login screen
                    } else {
                        Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Navigate to RegisterFragment
    private fun navigateToRegisterFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}
