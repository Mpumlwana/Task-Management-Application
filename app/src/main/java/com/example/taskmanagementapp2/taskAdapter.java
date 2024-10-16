package com.example.taskmanagementapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {

    private final List<task> tasks;
    private final int fragmentType;
    private final OnTaskDoneListener taskDoneListener;

    public taskAdapter(List<task> tasks, int fragmentType, OnTaskDoneListener taskDoneListener) {
        this.tasks = tasks;
        this.fragmentType = fragmentType;
        this.taskDoneListener = taskDoneListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        task currentTask = tasks.get(position);
        holder.taskTitle.setText(currentTask.getTitle());
        holder.taskDescription.setText(currentTask.getDescription());

        // Show "Mark as Done" button for Today and Upcoming tasks
        if (fragmentType != taskFragment.TYPE_DONE) {
            holder.markAsDoneButton.setVisibility(View.VISIBLE);
            holder.markAsDoneButton.setOnClickListener(v -> {
                currentTask.setDone(true); // Mark the task as done
                notifyDataSetChanged(); // Refresh the RecyclerView
            });
        } else {
            holder.markAsDoneButton.setVisibility(View.GONE); // Hide the button for Done tasks
        }

        // Set background color based on fragmentType
        switch (fragmentType) {
            case taskFragment.TYPE_TODAY:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.red));
                break;
            case taskFragment.TYPE_UPCOMING:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.green));
                break;
            case taskFragment.TYPE_DONE:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.yellow));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTitle;
        private final TextView taskDescription;
        private final Button markAsDoneButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_description);
            markAsDoneButton = itemView.findViewById(R.id.mark_as_done_button); // Make sure this button exists in the item_task.xml layout
        }
    }

    public interface OnTaskDoneListener {
        void onTaskDone(task task);
    }
}
