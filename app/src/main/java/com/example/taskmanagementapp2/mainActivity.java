package com.example.taskmanagementapp2; // Adjust the package name as per your project

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class mainActivity extends AppCompatActivity {

    // Declare variables for the Spinner (for subjects) and ListView (for grades)
    private Spinner subjectSpinner;
    private ListView gradesList;

    // Array of subjects available for selection
    private String[] subjects = {"AppDev3", "Project3", "Information System3", "ADT3", "ICE"};

    // Map to store grades corresponding to each subject
    private Map<String, String[]> gradesMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this layout exists in res/layout

        // Initialize the Spinner and ListView from the layout file
        subjectSpinner = findViewById(R.id.subject_spinner);
        gradesList = findViewById(R.id.grades_list);

        // Populate the grades map with data for each subject
        gradesMap.put("AppDev3", new String[]{"Module 1: 85%", "Module 2: 88%", "Final Exam: 90%"});
        gradesMap.put("Project3", new String[]{"Phase 1: 75%", "Phase 2: 80%", "Final Project: 85%"});
        gradesMap.put("Information System3", new String[]{"Assignment 1: 82%", "Assignment 2: 85%", "Final Exam: 88%"});
        gradesMap.put("ADT3", new String[]{"Test 1: 78%", "Test 2: 81%", "Final Exam: 87%"});
        gradesMap.put("ICE", new String[]{"Activity 1: 92%", "Activity 2: 94%", "Final Exam: 96%"});

        // Create an adapter to populate the Spinner with subjects
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);

        // Set up the Spinner's item selection behavior
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected subject and fetch the corresponding grades
                String selectedSubject = subjects[position];
                String[] selectedGrades = gradesMap.get(selectedSubject);

                // Update the ListView with the selected subject's grades
                ArrayAdapter<String> gradesAdapter = new ArrayAdapter<>(mainActivity.this, android.R.layout.simple_list_item_1, selectedGrades);
                gradesList.setAdapter(gradesAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if no subject is selected
            }
        });
    }
}
