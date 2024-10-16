package com.example.taskmanagementapp2

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class TaskFragmentTest {

    @Test
    fun fragment_isInitializedCorrectly() {
        // Create sample tasks
        val tasks = mutableListOf<task>()
        val calendar = Calendar.getInstance()

        calendar.set(2024, Calendar.OCTOBER, 15)
        tasks.add(task("Sample Task 1", "Description 1", calendar.time, false))

        calendar.set(2024, Calendar.OCTOBER, 16)
        tasks.add(task("Sample Task 2", "Description 2", calendar.time, false))

        // Launch the fragment with arguments
        val fragmentArgs = Bundle().apply {
            putInt("fragment_type", taskFragment.TYPE_TODAY)
            putSerializable("tasks", ArrayList(tasks))
        }
        val scenario = launchFragmentInContainer<taskFragment>(fragmentArgs)

        // Move the fragment to the RESUMED state
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Verify that the fragment was initialized correctly
        scenario.onFragment { fragment ->
            assertNotNull(fragment)
            assertEquals(taskFragment.TYPE_TODAY, fragment.fragmentType)
            assertEquals(2, fragment.tasks.size)
        }
    }
}
