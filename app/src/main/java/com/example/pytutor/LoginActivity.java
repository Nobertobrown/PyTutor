package com.example.pytutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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

        // Find the "Sign Up Here" button by ID (Add this line)
        Button signupButton = findViewById(R.id.signupButton); // <-- Add this line

        // Set the click listener for the "Sign Up Here" button (Add this block)
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the button is clicked, open the Sign Up Activity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class); // <-- Add this line
                startActivity(intent); // <-- Add this line
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_from_left);
            }
        });
    }
}