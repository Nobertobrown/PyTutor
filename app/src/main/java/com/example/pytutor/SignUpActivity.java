package com.example.pytutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignUpActivity";
    public EditText username, email, password, confirm_password;
    public Button sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find the "Login Here" link by ID (Add this line)
        TextView loginLink = findViewById(R.id.loginLink); // <-- Add this line
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmPassword);
        sign_up_btn = findViewById(R.id.signUpBtn);

        // Set the click listener for the "Sign Up Here" button (Add this block)
        loginLink.setOnClickListener(v -> {
            // When the button is clicked, open the Sign Up Activity
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class); // <-- Add this line
            startActivity(intent); // <-- Add this line
        });
        sign_up_btn.setOnClickListener(this::handleSignUp);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void handleSignUp(View view) {
        String emailText = email.getText().toString().trim();
        String usernameText = username.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirm_password.getText().toString().trim();
        String message;

        if (emailText.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            message = "Enter a valid email address!";
            email.setError(message);
        } else if (usernameText.isBlank()) {
            message = "This is a required field!";
            username.setError(message);
        } else if (passwordText.isBlank()) {
            message = "Password is a required field!";
            password.setError(message);
        } else if (!passwordText.equals(confirmPasswordText)) {
            message = "Entered passwords don't match!";
            confirm_password.setError(message);
        } else {
            createAccount(emailText, passwordText, usernameText);
        }
    }

    private void createAccount(String email, String password, String username) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            // Now update the display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            Log.d(TAG, "User profile updated with username.");
                                            updateUI(user); // or go to main activity
                                        }
                                    });
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END create_user_with_email]
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