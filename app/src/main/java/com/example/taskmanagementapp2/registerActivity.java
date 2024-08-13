package com.example.taskmanagementapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get references to the EditText fields
        EditText firstNameEditText = findViewById(R.id.first_name);
        EditText lastNameEditText = findViewById(R.id.last_name);
        EditText emailEditText = findViewById(R.id.email_address);
        EditText passwordEditText = findViewById(R.id.password);

        Button registerButton = findViewById(R.id.register_button);
        Button backToSplashButton = findViewById(R.id.back_to_splash_button);

        // Set onClick listeners for the buttons
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle registration logic
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate inputs or save data as needed

                // Navigate to the HomeActivity after registration
                Intent intent = new Intent(registerActivity.this, homeActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the RegisterActivity so it's not in the back stack
            }
        });

        backToSplashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the SplashActivity
                Intent intent = new Intent(registerActivity.this, splashActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the RegisterActivity
            }
        });
    }
}