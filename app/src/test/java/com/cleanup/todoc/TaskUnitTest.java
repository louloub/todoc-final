package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRoomRepository;
import com.cleanup.todoc.data.repository.TaskRoomRepository;
import com.cleanup.todoc.model.TaskCellModelUi;
import com.cleanup.todoc.model.TasksModelUi;
import com.cleanup.todoc.ui.viewmodel.SortMethod;
import com.cleanup.todoc.ui.viewmodel.TaskViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ProjectRoomRepository projectRoomRepository;

    @Mock
    private TaskRoomRepository taskRoomRepository;

    private TaskViewModel viewModel;

    private MutableLiveData<List<Project>> projectsLiveData;
    private MutableLiveData<List<Task>> tasksLiveData;

    @Before
    public void setUp() {
        projectsLiveData = new MutableLiveData<>();
        tasksLiveData = new MutableLiveData<>();

        given(projectRoomRepository.getProjectListLiveData()).willReturn(projectsLiveData);
        given(taskRoomRepository.getTaskListLiveData()).willReturn(tasksLiveData);

        viewModel = new TaskViewModel(projectRoomRepository, taskRoomRepository);
    }

    @Test
    public void given_one_project_and_one_task_should_expose_one_TasksModelUi() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasksLiveData.setValue(tasks);

        // When
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(1, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 1", tasksModelUi.getTaskCellModels().get(0).getName());
    }

    @Test
    public void test_az_comparator() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasks.add(new Task(1, "task 2", new Date().getTime()));
        tasks.add(new Task(2, "task 3", new Date().getTime()));
        tasksLiveData.setValue(tasks);

        // When
        // viewModel.setSortMethod(SortMethod.ALPHABETICAL);
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());
        // List<TasksModelUi> tasksModelUiList = LiveDataTestUtil.getOrAwaitValueList(viewModel.getTaskModelUiMediatorLiveData());
        // List<TaskCellModelUi> taskCellModelUiList = LiveDataTestUtil.getOrAwaitValueListCell(viewModel.getTaskModelUiMediatorLiveData());

        List<TaskCellModelUi> result = tasksModelUi.getTaskCellModels();
        result.add(tasksModelUi.getTaskCellModels().get(0));
        result.add(tasksModelUi.getTaskCellModels().get(1));
        result.add(tasksModelUi.getTaskCellModels().get(2));

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(1, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 1", tasksModelUi.getTaskCellModels().get(0).getName());
        assertEquals("task 1", result.get(0).getName());
        assertEquals("task 2", result.get(1).getName());
        assertEquals("task 3", result.get(2).getName());
    }

    /*

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
    }*/
}