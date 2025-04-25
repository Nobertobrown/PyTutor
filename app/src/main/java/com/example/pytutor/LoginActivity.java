package com.example.pytutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText email, password;
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find the "Sign Up Here" button by ID (Add this line)
        Button signupButton = findViewById(R.id.signupButton); // <-- Add this line
        Button loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.editTextTextPassword);

        // Set the click listener for the "Sign Up Here" button (Add this block)
        signupButton.setOnClickListener(v -> {
            // When the button is clicked, open the Sign Up Activity
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class); // <-- Add this line
            startActivity(intent); // <-- Add this line
        });

        // Set the click listener for the "Login" button (Add this block)
        loginButton.setOnClickListener(v -> {
            // When the button is clicked, open the Sign Up Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // <-- Add this line
            startActivity(intent); // <-- Add this line
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    @Override
    public void onClick(View v) {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String message;

        if (emailInput.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            message = "Enter a valid email address!";
            email.setError(message);
//           You can display an error in the component using email.setEror('Error msg');
        } else if (passwordInput.isBlank()) {
            message = "Password is a required field!";
            password.setError(message);
        } else {
            signIn(emailInput, passwordInput);
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uid", user.getUid());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("name", user.getDisplayName());
            startActivity(intent);
            this.finish();
        }
    }
}