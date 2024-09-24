package com.example.taskmanagementapp2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Calendar;
import androidx.annotation.Nullable;
import java.util.List;

public class taskOrganizationActivity extends AppCompatActivity {
    private List<task> tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_organization);

        // Initialize the list of tasks
        tasks = new ArrayList<>();

        // Create some hardcoded tasks
        Calendar calendar = Calendar.getInstance();

        // Today's task
        calendar.set(2024, Calendar.AUGUST, 23);
        tasks.add(new task("Finish Project", "Complete the Android project", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 23);
        tasks.add(new task("Information Systems", "I have to submit the LinkedIn Certificate", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 23);
        tasks.add(new task("Applications Development Capstone Project", "I have to submit the Backend part", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 23);
        tasks.add(new task("Internship Stand-Up Meeting", "I had to attend a stand-up meeting", calendar.getTime(), false));

        // Upcoming task
        calendar.set(2024, Calendar.AUGUST, 25);
        tasks.add(new task("Prepare Presentation", "Prepare slides for presentation", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 26);
        tasks.add(new task("Stand-Up Meeting", "Each and everyday I have to attend stand up meeting.", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 27);
        tasks.add(new task("Applications development theory ", "I have to submit the test.", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 28);
        tasks.add(new task("Applications development Practice ", "I have to submit the Capstone project.", calendar.getTime(), false));

        calendar.set(2024, Calendar.AUGUST, 30);
        tasks.add(new task("Prepare Presentation  ", "I have to prepare my self for presenting at work.", calendar.getTime(), false));

        // Done task
        calendar.set(2024, Calendar.AUGUST, 8);
        tasks.add(new task("Submit Report", "Submit the final report", calendar.getTime(), true));

        calendar.set(2024, Calendar.AUGUST, 15);
        tasks.add(new task("Write class test", "prepare my self for test", calendar.getTime(), true));

        calendar.set(2024, Calendar.AUGUST, 16);
        tasks.add(new task("Stand-Up meeting", "Prepare my self for a meeting.", calendar.getTime(), true));

        calendar.set(2024, Calendar.AUGUST, 17);
        tasks.add(new task("Class test for ITS", "I have to prepare my self for a test", calendar.getTime(), true));

        calendar.set(2024, Calendar.AUGUST, 18);
        tasks.add(new task("Group meeting", "I have a meeting with my group members.", calendar.getTime(), true));

        calendar.set(2024, Calendar.AUGUST, 18);
        tasks.add(new task("Submit Report", "Submit the final report", calendar.getTime(), true));

        // Set up TabLayout and ViewPager2
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        taskPagerAdapter adapter = new taskPagerAdapter(this, tasks);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Today");
                    break;
                case 1:
                    tab.setText("Upcoming");
                    break;
                case 2:
                    tab.setText("Done");
                    break;
            }
        }).attach();
    }
}


