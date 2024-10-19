package com.example.taskmanagementapp2; // Adjust the package name as per your project

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class mainActivity extends AppCompatActivity {

    private Spinner subjectSpinner;
    private ListView gradesList;
    private Button viewGradesButton;
    private databaseHelper dbHelper;
    private List<String> subjectNames = new ArrayList<>();
    private List<Integer> subjectIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        subjectSpinner = findViewById(R.id.subject_spinner);
        gradesList = findViewById(R.id.grades_list);
        viewGradesButton = findViewById(R.id.view_grades_button);

        // Initialize the database helper
        dbHelper = new databaseHelper(this);

        // Load subjects from the database
        loadSubjectsFromDatabase();

        // Set up button click functionality
        setupButton();
    }

    private void loadSubjectsFromDatabase() {
        Cursor subjectsCursor = dbHelper.getAllSubjects();
        if (subjectsCursor != null) {
            while (subjectsCursor.moveToNext()) {
                // Add subject names and IDs to lists
                @SuppressLint("Range") int subjectId = subjectsCursor.getInt(subjectsCursor.getColumnIndex("ID"));
                @SuppressLint("Range") String subjectName = subjectsCursor.getString(subjectsCursor.getColumnIndex("NAME"));
                subjectIds.add(subjectId);
                subjectNames.add(subjectName);
            }
            subjectsCursor.close();

            // Create an adapter for the Spinner
            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectNames);
            subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(subjectAdapter);
        }
    }

    private void setupButton() {
        // View grades button functionality
        viewGradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedSubjectPosition = subjectSpinner.getSelectedItemPosition();
                if (selectedSubjectPosition != AdapterView.INVALID_POSITION) {
                    int subjectId = subjectIds.get(selectedSubjectPosition);
                    loadGradesForSubject(subjectId);
                }
            }
        });
    }

    private void loadGradesForSubject(int subjectId) {
        Cursor gradesCursor = dbHelper.getGradesBySubject(subjectId);
        List<String> grades = new ArrayList<>();

        if (gradesCursor != null) {
            while (gradesCursor.moveToNext()) {
                @SuppressLint("Range") String gradeName = gradesCursor.getString(gradesCursor.getColumnIndex("GRADE_NAME"));
                @SuppressLint("Range") int gradeValue = gradesCursor.getInt(gradesCursor.getColumnIndex("GRADE_VALUE"));
                grades.add(gradeName + ": " + gradeValue + "%");
            }
            gradesCursor.close();

            // Create an adapter to display the grades in the ListView
            ArrayAdapter<String> gradesAdapter = new ArrayAdapter<>(mainActivity.this, android.R.layout.simple_list_item_1, grades);
            gradesList.setAdapter(gradesAdapter);



        }
    }
}
