package com.example.recipeapp

import AppDatabase
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.authintication.SharedPrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.recipeapp.authintication.User

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var db: AppDatabase
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var loginButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.password)
        confirmPasswordEditText = view.findViewById(R.id.etConfirmPassword)
        registerButton = view.findViewById(R.id.btnRegister)
        loginButton = view.findViewById(R.id.loginbtn)
        db = AppDatabase.getDatabase(requireContext())
        sharedPrefManager = SharedPrefManager(requireContext())

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (validateInput(email, password, confirmPassword)) {
                registerUser(email, password)
            }
        }

        // Set up register button click listener
        loginButton.setOnClickListener {
            navigateToLoginFragment()
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(email = email, password = password)
            db.userDao().insert(user)
            withContext(Dispatchers.Main) {
                sharedPrefManager.saveLoginStatus(true)
                val intent = Intent(requireContext(), RecipeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()  // This prevents the user from navigating back to the AuthActivity
            }
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}