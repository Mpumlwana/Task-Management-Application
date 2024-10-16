package com.example.taskmanagementapp2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class taskPagerAdapter extends FragmentStateAdapter {
   // private List<task> tasks;
    private List<task>today,upcoming,done;

    public taskPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<task> today,List<task> upcoming,List<task>done) {
        super(fragmentActivity);
        //this.tasks = tasks;
        this.today=today;
        this.upcoming=upcoming;
        this.done=done;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return taskFragment.newInstance(taskFragment.TYPE_TODAY, today);
            case 1:
                return taskFragment.newInstance(taskFragment.TYPE_UPCOMING, upcoming);
            case 2:
                return taskFragment.newInstance(taskFragment.TYPE_DONE, done);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}