package com.example.taskmanagementapp2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText dateEditText,timeEditText;
    databaseHelper db;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);
        //initialize the database
        db= new databaseHelper(this);
        //get references of the EditText fields

        EditText titleEditText=findViewById(R.id.addTaskTitle);
        EditText CategoryEditText=findViewById(R.id.addTaskCategory);
        EditText dateEditeText=findViewById(R.id.taskDate);
        EditText timeEditText=findViewById(R.id.taskTime);

        EditText DescriptionEdittext= findViewById(R.id.task_Description);
        Button CreateTaskButton =findViewById(R.id.createTask);
        // set action onclick listeners for date and time fields
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        //set action onclick listeners for the text fields and buttons
        CreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String category= CategoryEditText.getText().toString();
                String description=DescriptionEdittext.getText().toString();
                String date= dateEditeText.getText().toString();
                String time=timeEditText.getText().toString();
               // save task to the database
                boolean isInserted=db.insertTask(title,category,date,time,description);
                if(isInserted){
                    Toast.makeText(CreateTaskActivity.this,"Task created successfully!",Toast.LENGTH_SHORT).show();

                // navigate to taskOrganizationActivity activity and pass the newly created task
                Intent intent = new Intent(CreateTaskActivity.this, taskOrganizationActivity.class);
                startActivity(intent);
                finish();
            }else {
                    Toast.makeText(CreateTaskActivity.this,"failed to create task",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    // Method to show DatePickerDialog
    private void showDatePicker() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set selected date in the EditText
                        dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Method to show TimePickerDialog
    private void showTimePicker() {
        // Get current time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Set selected time in the EditText
                        timeEditText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }



}
