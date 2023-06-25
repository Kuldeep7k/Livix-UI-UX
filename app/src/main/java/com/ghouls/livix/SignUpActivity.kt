package com.ghouls.livix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var nextButton: Button
    private lateinit var goBack: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        nextButton = findViewById(R.id.nextButton)
        goBack = findViewById(R.id.goBackButton)

        nextButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInput(username, email, password, confirmPassword)) {
                // Add the user to the "users" collection
                firestore.collection("users")
                    .add(hashMapOf(
                        "username" to username,
                        "email" to email,
                        "password" to password
                    ))
                    .addOnSuccessListener { documentReference ->
                        // User added successfully
                        showToast("User created successfully")
                        navigateToUserDetailsActivity()
                    }
                    .addOnFailureListener { exception ->
                        // An error occurred while adding the user
                        val error = exception.message ?: "Unknown error"
                        showToast("Error adding user: $error")
                    }
            }
        }

        goBack.setOnClickListener {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
        }
    }

    private fun validateInput(username: String, email: String, password: String, confirmPassword: String): Boolean {
        // Check if username is empty
        if (username.isEmpty()) {
            showToast("Please enter a username")
            return false
        }

        // Check if email is empty or invalid
        if (email.isEmpty()) {
            showToast("Please enter an email address")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address")
            return false
        }

        // Check if password is empty or less than 6 characters
        if (password.isEmpty()) {
            showToast("Please enter a password")
            return false
        } else if (password.length < 6) {
            showToast("Password must be at least 6 characters long")
            return false
        }

        // Check if password and confirm password match
        if (password != confirmPassword) {
            showToast("Passwords don't match")
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToUserDetailsActivity() {
        val userDetailsIntent = Intent(this, addDetails::class.java)
        startActivity(userDetailsIntent)
    }

}
