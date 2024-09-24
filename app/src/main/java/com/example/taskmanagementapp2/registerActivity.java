package com.example.taskmanagementapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {

    databaseHelper db;  // Database helper for interacting with SQLite
    EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    Button registerButton, backToSplashButton;
    TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize DatabaseHelper
        db = new databaseHelper(this);

        // Get references to the EditText fields
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email_address);
        passwordEditText = findViewById(R.id.password);

        // Get references to the buttons and TextView
        registerButton = findViewById(R.id.register_button);
        backToSplashButton = findViewById(R.id.back_to_splash_button);
        signInTextView = findViewById(R.id.sign_in_text);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle registration logic
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(registerActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@")) {
                    // Validate the email address contains "@"
                    Toast.makeText(registerActivity.this, "Invalid email address, please try again", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert user data into the database
                    boolean isInserted = db.insertUser(firstName, lastName, email, password);
                    if (isInserted) {
                        Toast.makeText(registerActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        // Navigate to the LoginActivity after successful registration
                        Intent intent = new Intent(registerActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();  // Optional: Close the RegisterActivity
                    } else {
                        Toast.makeText(registerActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backToSplashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the SplashActivity
                Intent intent = new Intent(registerActivity.this, splashActivity.class);
                startActivity(intent);
                finish();  // Optional: Close the RegisterActivity
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the LoginActivity
                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                startActivity(intent);
                finish();  // Optional: Close the RegisterActivity
            }
        });
    }
}
