package com.cleanup.todoc;

import android.bluetooth.BluetoothGattDescriptor;

import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.TaskCellModelUi;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {

    @Test
    public void test_projects() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);

        // THEN
        assertEquals("Projet Tartampion", task1.getProjectName());
        assertEquals("Projet Lucidia", task2.getProjectName());
        assertEquals("Projet Circus", task3.getProjectName());
    }

    @Test
    public void should_have_one_task_in_list_after_add_one_Task() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        List<TaskCellModelUi> taskCellModelUiList = new ArrayList<>();
        taskCellModelUiList.add(task1);

        // THEN
        assertEquals(1,taskCellModelUiList.size());
    }

    @Test
    public void should_have_three_task_in_list_after_add_three_task() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);
        List<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // THEN
        assertEquals(3,tasks.size());
    }

    @Test
    public void should_have_two_task_in_list_after_add_three_task_and_delete_one_Task() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);
        List<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        tasks.remove(0);

        // THEN
        assertEquals(2,tasks.size());
        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
    }

    @Test
    public void should_dont_have_task_in_list_after_add_three_task_and_delete_three_task() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);
        List<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        tasks.remove(0);
        tasks.remove(0);
        tasks.remove(0);

        // THEN
        assertEquals(0,tasks.size());
    }

    @Test
    public void should_have_task_in_good_order_after_az_comparator() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        Collections.sort(tasks, new TaskCellModelUi.TaskAZComparator());

        // THEN
        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void should_have_task_in_good_order_after_za_comparator() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        Collections.sort(tasks, new TaskCellModelUi.TaskZAComparator());

        // THEN
        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void should_have_task_in_good_order_after_recent_comparator() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        Collections.sort(tasks, new TaskCellModelUi.TaskRecentComparator());

        // THEN
        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void should_have_task_in_good_order_after_old_comparator() {
        // GIVEN
        final TaskCellModelUi task1 = new TaskCellModelUi(1,"Projet Tartampion","task 1",123,0);
        final TaskCellModelUi task2 = new TaskCellModelUi(2,"Projet Lucidia","task 2",124,1);
        final TaskCellModelUi task3 = new TaskCellModelUi(3,"Projet Circus","task 3",125,2);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // WHEN
        Collections.sort(tasks, new TaskCellModelUi.TaskOldComparator());

        // THEN
        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}