package com.example.taskmanagementapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splush); // Link to the splash screen layout

        // Delay and navigate to the appropriate activity after the splash screen
        new Handler().postDelayed(() -> {
            // Check if the user is logged in
            boolean isLoggedIn = checkUserLoggedIn(); // Implement this method based on your logic
            Intent intent;
            if (isLoggedIn) {
                // Navigate to HomeActivity if the user is logged in
                intent = new Intent(splashActivity.this, homeActivity.class);
            } else {
                // Navigate to LoginActivity if the user is not logged in
                intent = new Intent(splashActivity.this, loginActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean checkUserLoggedIn() {
        return false;
    }
}

