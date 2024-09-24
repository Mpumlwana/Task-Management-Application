package com.example.taskmanagementapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    databaseHelper db;  // Database helper for interacting with SQLite
    EditText emailEditText, passwordEditText;
    Button loginButton, backToSplashButton;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Link to the XML layout file

        // Initialize DatabaseHelper
        db = new databaseHelper(this);

        // Get references to the buttons and input fields
        loginButton = findViewById(R.id.login_button);
        backToSplashButton = findViewById(R.id.back_to_splash_button);
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        signUpText = findViewById(R.id.sign_up_text);

        // Set onClick listener for the sign-up text
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this, registerActivity.class);
            startActivity(intent);
        });

        // In the loginButton's onClickListener in LoginActivity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (db.checkUser(email, password)) {
                    // Retrieve the user's first and last name from the database
                    Cursor cursor = db.getUserData(email);
                    if (cursor != null && cursor.moveToFirst()) {
                        // Ensure column names are correct
                        int firstNameIndex = cursor.getColumnIndex("FIRSTNAME");
                        int lastNameIndex = cursor.getColumnIndex("LASTNAME");

                        // Check if columns exist
                        if (firstNameIndex != -1 && lastNameIndex != -1) {
                            String firstName = cursor.getString(firstNameIndex);
                            String lastName = cursor.getString(lastNameIndex);
                            cursor.close();

                            // Pass the first name and last name to the homeActivity
                            Intent intent = new Intent(loginActivity.this, homeActivity.class);
                            intent.putExtra("first_name", firstName);
                            intent.putExtra("last_name", lastName);
                            startActivity(intent);
                            finish(); // Close LoginActivity
                        } else {
                            // Handle missing columns
                            Toast.makeText(loginActivity.this, "Error retrieving user data!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle cursor issues
                        Toast.makeText(loginActivity.this, "No user data found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle login failure
                    Toast.makeText(loginActivity.this, "Invalid email or password, Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClick listener for the back to splash button
        backToSplashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the SplashActivity
                Intent intent = new Intent(loginActivity.this, splashActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the LoginActivity
            }
        });
    }
}

