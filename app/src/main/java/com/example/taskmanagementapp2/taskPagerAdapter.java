package com.example.taskmanagementapp2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class taskPagerAdapter extends FragmentStateAdapter {
    private List<task> tasks;

    public taskPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<task> tasks) {
        super(fragmentActivity);
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return taskFragment.newInstance(taskFragment.TYPE_TODAY, tasks);
            case 1:
                return taskFragment.newInstance(taskFragment.TYPE_UPCOMING, tasks);
            case 2:
                return taskFragment.newInstance(taskFragment.TYPE_DONE, tasks);
            default:
                return taskFragment.newInstance(taskFragment.TYPE_TODAY, tasks);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}