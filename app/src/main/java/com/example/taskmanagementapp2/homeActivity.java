package com.example.taskmanagementapp2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get reference to the welcome message TextView
        TextView welcomeMessage = findViewById(R.id.welcome_message);

        // Get the user's first name and last name passed from LoginActivity
        String firstName = getIntent().getStringExtra("first_name");
        String lastName = getIntent().getStringExtra("last_name");

        // Set the welcome message with the user's name
        if (firstName != null && lastName != null) {
            welcomeMessage.setText("Welcome, " + firstName + " " + lastName + "!");
        } else {
            welcomeMessage.setText("Welcome!");
        }

        // Set up buttons
        Button createTaskButton = findViewById(R.id.create_task_button);
        Button activitiesButton = findViewById(R.id.activities_button);
        Button prioritizeButton = findViewById(R.id.prioritize_button);
        Button taskMasterButton = findViewById(R.id.task_master_button);
        Button analyticsButton = findViewById(R.id.analytics_button);
        Button exitButton = findViewById(R.id.exit_button);

        // Add OnClickListener to the Exit button
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the SplashActivity
                Intent intent = new Intent(homeActivity.this, splashActivity.class);
                startActivity(intent);
                finish(); // Close HomeActivity
            }
        });

        // Add OnClickListener to the Prioritize button
        prioritizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to taskOrganizationActivity
                Intent intent = new Intent(homeActivity.this, taskOrganizationActivity.class);
                startActivity(intent);
            }
        });

        // Add OnClickListener to other buttons as needed
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreateTaskActivity
                Intent intent= new Intent(homeActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        activitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add functionality for activities
            }
        });

        taskMasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add functionality for task master
            }
        });

        analyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add functionality for analytics
            }
        });
    }
}


