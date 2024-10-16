package com.example.taskmanagementapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(
    private var reminders: List<Reminder>,
    private val onItemClick: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textViewTitle)
        val dateTimeTextView: TextView = view.findViewById(R.id.textViewDateTime)
        val recurringTextView: TextView = view.findViewById(R.id.textViewRecurring)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.titleTextView.text = reminder.title
        holder.dateTimeTextView.text = "${reminder.date} ${reminder.time}"
        holder.recurringTextView.text = if (reminder.recurring) "Recurring" else "One-time"

        holder.itemView.setOnClickListener { onItemClick(reminder) }
    }

    override fun getItemCount() = reminders.size

    fun updateReminders(newReminders: List<Reminder>) {
        reminders = newReminders
        notifyDataSetChanged()
    }
}