package com.example.taskmanagementapp2

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskmanagementapp2.taskFragment
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TaskFragmentInstrumentedTest {

    private lateinit var taskList: List<task>

    @Before
    fun setUp() {
        // Initialize the task list for testing
        val calendar = Calendar.getInstance()

        // Today's task
        calendar.set(2024, Calendar.OCTOBER, 15)
        val task1 = task("Test Task 1", "This is a test task.", calendar.time, false)

        // Upcoming task
        calendar.set(2024, Calendar.OCTOBER, 20)
        val task2 = task("Test Task 2", "This is another test task.", calendar.time, false)

        // Done task
        calendar.set(2024, Calendar.OCTOBER, 10)
        val task3 = task("Test Task 3", "This task is done.", calendar.time, true)

        taskList = listOf(task1, task2, task3)
    }

    @Test
    fun testTaskFragmentDisplayTasks() {
        // Create arguments to pass to the fragment
        val args = Bundle().apply {
            putInt("fragment_type", taskFragment.TYPE_TODAY)
            putSerializable("tasks", ArrayList(taskList))
        }

        // Launch the fragment with the task list
        val scenario = FragmentScenario.launchInContainer(taskFragment::class.java, args)

        // Check if the RecyclerView is populated
        scenario.onFragment { fragment ->
            val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.recyclerViewToday)
            assertNotNull(recyclerView)
            assertEquals(1, recyclerView?.adapter?.itemCount) // Only one task is due today
        }
    }
}
