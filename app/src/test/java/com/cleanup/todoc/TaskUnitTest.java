package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRoomRepository;
import com.cleanup.todoc.data.repository.TaskRoomRepository;
import com.cleanup.todoc.model.TaskCellModelUi;
import com.cleanup.todoc.model.TasksModelUi;
import com.cleanup.todoc.ui.SortMethod;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
    public void given_one_project_and_three_task_when_delete_one_task_then_two_task_are_exposed() {
        // Given
        TaskCellModelUi taskCellModelUi1 = mock(TaskCellModelUi.class);

        // When
        viewModel.deleteTask(taskCellModelUi1);

        // Then
        verify(taskRoomRepository).getTaskListLiveData();
        verify(taskRoomRepository).deleteTask(taskCellModelUi1.getId());
        verifyNoMoreInteractions(taskRoomRepository);
    }

    @Test
    public void given_one_project_and_three_task_when_az_comparator_then_task_are_good_sorting() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasks.add(new Task(0, "task 2", new Date().getTime()));
        tasks.add(new Task(0, "task 3", new Date().getTime()));
        tasksLiveData.setValue(tasks);

        // When
        viewModel.setSortMethod(SortMethod.ALPHABETICAL);
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());
        List<TaskCellModelUi> result = tasksModelUi.getTaskCellModels();

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(3, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 1", result.get(0).getName());
        assertEquals("task 2", result.get(1).getName());
        assertEquals("task 3", result.get(2).getName());
    }

    @Test
    public void given_one_project_and_three_task_when_za_comparator_then_task_are_good_sorting() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasks.add(new Task(0, "task 2", new Date().getTime()));
        tasks.add(new Task(0, "task 3", new Date().getTime()));
        tasksLiveData.setValue(tasks);

        // When
        viewModel.setSortMethod(SortMethod.ALPHABETICAL_INVERTED);
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());
        List<TaskCellModelUi> result = tasksModelUi.getTaskCellModels();

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(3, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 3", result.get(0).getName());
        assertEquals("task 2", result.get(1).getName());
        assertEquals("task 1", result.get(2).getName());
    }

    @Test
    public void given_one_project_and_three_task_when_older_comparator_then_task_are_good_sorting() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasks.add(new Task(0, "task 2", new Date().getTime()));
        tasks.add(new Task(0, "task 3", new Date().getTime()));
        tasksLiveData.setValue(tasks);

        // When
        viewModel.setSortMethod(SortMethod.OLD_FIRST);
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());
        List<TaskCellModelUi> result = tasksModelUi.getTaskCellModels();

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(3, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 1", result.get(0).getName());
        assertEquals("task 2", result.get(1).getName());
        assertEquals("task 3", result.get(2).getName());
    }

    @Test
    public void given_one_project_and_three_task_when_recent_comparator_then_task_are_good_sorting() throws InterruptedException {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("First project"));
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(0, "task 1", new Date().getTime()));
        tasks.add(new Task(0, "task 2", new Date().getTime()+1));
        tasks.add(new Task(0, "task 3", new Date().getTime()+2));
        tasksLiveData.setValue(tasks);

        // When
        viewModel.setSortMethod(SortMethod.RECENT_FIRST);
        TasksModelUi tasksModelUi = LiveDataTestUtil.getOrAwaitValue(viewModel.getTaskModelUiMediatorLiveData());
        List<TaskCellModelUi> result = tasksModelUi.getTaskCellModels();

        // Then
        assertFalse(tasksModelUi.isEmptyStateDisplayed());
        assertEquals(0, tasksModelUi.getEmptyTaskNameErrorStringRes());
        assertNotNull(tasksModelUi.getTaskCellModels());
        assertEquals(3, tasksModelUi.getTaskCellModels().size());
        assertEquals("task 3", result.get(0).getName());
        assertEquals("task 2", result.get(1).getName());
        assertEquals("task 1", result.get(2).getName());
    }
}