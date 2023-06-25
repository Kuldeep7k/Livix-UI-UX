package com.ghouls.livix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class addDetails : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var uploadButton: Button
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var middleNameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_details)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance()

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView)
        uploadButton = findViewById(R.id.uploadButton)
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        middleNameEditText = findViewById(R.id.middleNameEditText)
        locationEditText = findViewById(R.id.locationEditText)
        saveButton = findViewById(R.id.saveButton)

        uploadButton.setOnClickListener {
            // Handle profile photo upload
            // You can implement your own logic for selecting an image from gallery or taking a photo
            // and upload the selected image to Firebase Storage.
        }

        saveButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val middleName = middleNameEditText.text.toString().trim()
            val location = locationEditText.text.toString().trim()

            if (validateInput(firstName, lastName, middleName, location)) {
                saveUserDetailsToFirestore(firstName, lastName, middleName, location)
            }
        }
    }

    private fun validateInput(firstName: String, lastName: String, middleName: String, location: String): Boolean {
        // Check if first name is empty
        if (firstName.isEmpty()) {
            showToast("Please enter your first name")
            return false
        }

        // Check if last name is empty
        if (lastName.isEmpty()) {
            showToast("Please enter your last name")
            return false
        }

        // Check if middle name is empty
        if (middleName.isEmpty()) {
            showToast("Please enter your middle name")
            return false
        }

        // Check if location is empty
        if (location.isEmpty()) {
            showToast("Please enter your location")
            return false
        }

        return true
    }

    private fun saveUserDetailsToFirestore(firstName: String, lastName: String, middleName: String, location: String) {
        // Save user details to Firestore
        // You can create a new document in the "users" collection and store the details
        // such as first name, last name, middle name, location, etc.

        // Example code to add user details to Firestore
        val user = hashMapOf(
            "first_name" to firstName,
            "last_name" to lastName,
            "middle_name" to middleName,
            "location" to location
        )

        firestore.collection("users")
            .document("<user-id>")
            .set(user)
            .addOnSuccessListener {
                showToast("User details saved successfully")
                navigateToMainActivity()
            }
            .addOnFailureListener { exception ->
                showToast("Error saving user details: ${exception.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}


