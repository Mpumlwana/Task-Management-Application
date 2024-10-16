package com.example.taskmanagementapp2;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class taskFragment extends Fragment {

    public static final int TYPE_TODAY = 0;
    public static final int TYPE_UPCOMING = 1;
    public static final int TYPE_DONE = 2;

    protected int fragmentType;
    protected List<task> tasks;
    private taskAdapter adapter;

    public static taskFragment newInstance(int type, List<task> tasks) {
        taskFragment fragment = new taskFragment();
        Bundle args = new Bundle();
        args.putInt("fragment_type", type);
        args.putSerializable("tasks", (ArrayList<task>) tasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getInt("fragment_type");
            tasks = (List<task>) getArguments().getSerializable("tasks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId;
        int recyclerViewId;
        switch (fragmentType) {
            case TYPE_TODAY:
                layoutId = R.layout.fragment_today;
                recyclerViewId = R.id.recyclerViewToday;
                break;
            case TYPE_UPCOMING:
                layoutId = R.layout.fragment_upcoming;
                recyclerViewId = R.id.recyclerViewUpcoming;
                break;
            case TYPE_DONE:
                layoutId = R.layout.fragment_done;
                recyclerViewId = R.id.recyclerViewDone;
                break;
            default:
                layoutId = R.layout.fragment_today;
                recyclerViewId = R.id.recyclerViewToday;
        }

        View view = inflater.inflate(layoutId, container, false);
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Filter the tasks based on the fragment type
        List<task> filteredTasks = filterTasksByType(fragmentType);

        // Initialize the adapter and pass the "mark as done" listener
        adapter = new taskAdapter(filteredTasks, fragmentType, this::markTaskAsDone);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Method to mark a task as done
    private void markTaskAsDone(task completedTask) {
        completedTask.setDone(true); // Set the task as done

        // Remove the task from the current list (for Today and Upcoming fragments)
        if (fragmentType != TYPE_DONE) {
            tasks.remove(completedTask);
        }

        // Notify the adapter about the dataset change to refresh the list
        adapter.notifyDataSetChanged();
    }

    // Filter tasks based on the fragment type
    private List<task> filterTasksByType(int type) {
        Date today = new Date();
        switch (type) {
            case TYPE_TODAY:
                return tasks.stream()
                        .filter(task -> isSameDay(task.getDueDate(), today) && !task.isDone())
                        .collect(Collectors.toList());
            case TYPE_UPCOMING:
                return tasks.stream()
                        .filter(task -> task.getDueDate().after(today) && !task.isDone())
                        .collect(Collectors.toList());
            case TYPE_DONE:
                return tasks.stream()
                        .filter(task::isDone)
                        .collect(Collectors.toList());
            default:
                return tasks;
        }
    }

    // Helper method to check if two dates are the same
    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return sdf.format(date1).equals(sdf.format(date2));
    }
}
