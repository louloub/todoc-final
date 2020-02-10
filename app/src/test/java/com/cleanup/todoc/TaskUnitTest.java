package com.cleanup.todoc;

import com.cleanup.todoc.model.TaskCellModelUi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
        final TaskCellModelUi task1 = new TaskCellModelUi(1, 1, "task 1", new Date().getTime());
        final TaskCellModelUi task2 = new TaskCellModelUi(2, 2, "task 2", new Date().getTime());
        final TaskCellModelUi task3 = new TaskCellModelUi(3, 3, "task 3", new Date().getTime());
        final TaskCellModelUi task4 = new TaskCellModelUi(4, 4, "task 4", new Date().getTime());

        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final TaskCellModelUi task1 = new TaskCellModelUi(1, 1, "aaa", 123);
        final TaskCellModelUi task2 = new TaskCellModelUi(2, 2, "zzz", 124);
        final TaskCellModelUi task3 = new TaskCellModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskCellModelUi.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final TaskCellModelUi task1 = new TaskCellModelUi(1, 1, "aaa", 123);
        final TaskCellModelUi task2 = new TaskCellModelUi(2, 2, "zzz", 124);
        final TaskCellModelUi task3 = new TaskCellModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskCellModelUi.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final TaskCellModelUi task1 = new TaskCellModelUi(1, 1, "aaa", 123);
        final TaskCellModelUi task2 = new TaskCellModelUi(2, 2, "zzz", 124);
        final TaskCellModelUi task3 = new TaskCellModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskCellModelUi.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final TaskCellModelUi task1 = new TaskCellModelUi(1, 1, "aaa", 123);
        final TaskCellModelUi task2 = new TaskCellModelUi(2, 2, "zzz", 124);
        final TaskCellModelUi task3 = new TaskCellModelUi(3, 3, "hhh", 125);

        final ArrayList<TaskCellModelUi> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskCellModelUi.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}