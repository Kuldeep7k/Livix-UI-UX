import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ghouls.livix.MainActivity
import com.ghouls.livix.R
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInput(username, password)) {
                loginUserFromFirestore(username, password)
            }
        }

        signUpButton.setOnClickListener {
            navigateToSignUpActivity()
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        // Check if username is empty
        if (username.isEmpty()) {
            showToast("Please enter a username")
            return false
        }

        // Check if password is empty
        if (password.isEmpty()) {
            showToast("Please enter a password")
            return false
        }

        return true
    }

    private fun loginUserFromFirestore(username: String, password: String) {
        // Query Firestore for the user with the provided username and password
        firestore.collection("users")
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // User found, login successful
                    showToast("Login successful")
                    navigateToMainActivity()
                } else {
                    // User not found, login failed
                    showToast("Invalid username or password")
                }
            }
            .addOnFailureListener { exception ->
                // An error occurred while querying the Firestore database
                showToast("Error logging in: ${exception.message}")
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

    private fun navigateToSignUpActivity() {
        val signUpActivityIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpActivityIntent)
        finish()
    }
}
