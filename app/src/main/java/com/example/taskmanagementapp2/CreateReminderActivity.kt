package com.example.taskmanagementapp2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CreateReminderActivity : AppCompatActivity() {

    private lateinit var database: ReminderDatabase
    private var reminderId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reminder)

        database = ReminderDatabase.getDatabase(this)
        reminderId = intent.getLongExtra("REMINDER_ID", -1L)

        val titleEditText: EditText = findViewById(R.id.editTextTitle)
        val dateButton: Button = findViewById(R.id.buttonDate)
        val timeButton: Button = findViewById(R.id.buttonTime)
        val recurringSwitch: Switch = findViewById(R.id.switchRecurring)
        val saveButton: Button = findViewById(R.id.buttonSave)

        if (reminderId != -1L) {
            // Load existing reminder data
            CoroutineScope(Dispatchers.Main).launch {
                val reminder = database.reminderDao().getReminderById(reminderId!!)
                titleEditText.setText(reminder.title)
                dateButton.text = reminder.date
                timeButton.text = reminder.time
                recurringSwitch.isChecked = reminder.recurring
            }
        }

        dateButton.setOnClickListener {
            showDatePicker(dateButton)
        }

        timeButton.setOnClickListener {
            showTimePicker(timeButton)
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val date = dateButton.text.toString()
            val time = timeButton.text.toString()
            val recurring = recurringSwitch.isChecked

            if (title.isNotEmpty() && date != "Select Date" && time != "Select Time") {
                val reminder = Reminder(
                    id = reminderId ?: 0,
                    title = title,
                    date = date,
                    time = time,
                    recurring = recurring
                )

                CoroutineScope(Dispatchers.IO).launch {
                    if (reminderId == -1L) {
                        database.reminderDao().insertReminder(reminder)
                    } else {
                        database.reminderDao().updateReminder(reminder)
                    }
                    runOnUiThread {
                        Toast.makeText(this@CreateReminderActivity, "Reminder saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker(dateButton: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            dateButton.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePicker(timeButton: Button) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            timeButton.text = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }
}