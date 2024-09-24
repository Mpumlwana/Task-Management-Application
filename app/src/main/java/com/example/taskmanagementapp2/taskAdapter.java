package com.example.taskmanagementapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {

    private final List<task> tasks;
    private final int fragmentType;

    public taskAdapter(List<task> tasks, int fragmentType) {
        this.tasks = tasks;
        this.fragmentType = fragmentType;
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

        // Set background color based on fragmentType
        switch (fragmentType) {
            case taskFragment.TYPE_TODAY:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.red)); // Set to red
                break;
            case taskFragment.TYPE_UPCOMING:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.green)); // Set to green
                break;
            case taskFragment.TYPE_DONE:
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.yellow)); // Set to yellow
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title); // Ensure this matches the XML ID
            taskDescription = itemView.findViewById(R.id.task_description);
        }
    }
}






