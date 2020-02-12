package com.cleanup.todoc.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.R;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ProjectRoomRepository;
import com.cleanup.todoc.data.repository.TaskRepository;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.model.TaskCellModelUi;
import com.cleanup.todoc.model.TasksModelUi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.cleanup.todoc.ui.viewmodel.SortMethod.ALPHABETICAL;

public class TaskViewModel extends ViewModel {

    private ProjectRoomRepository mProjectRoomRepository;
    private TaskRepository mTaskRepository;

    private MediatorLiveData<TasksModelUi> mTaskModelUiMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<SortMethod> mSortMethodLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsTaskNameEmpty = new MutableLiveData<>();

    public LiveData<TasksModelUi> getTaskModelUiMediatorLiveData() {
        return mTaskModelUiMediatorLiveData;
    }

    public TaskViewModel(ProjectRoomRepository projectRoomRepository, final TaskRepository taskRepository) {
        this.mProjectRoomRepository = projectRoomRepository;
        this.mTaskRepository = taskRepository;

        mTaskModelUiMediatorLiveData.addSource(mProjectRoomRepository.getProjectListLiveData(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(
                    projects,
                    taskRepository.getTaskListLiveData().getValue(),
                    mSortMethodLiveData.getValue(),
                    mIsTaskNameEmpty.getValue()
                    )
                );
            }
        });

        mTaskModelUiMediatorLiveData.addSource(mTaskRepository.getTaskListLiveData(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(
                    mProjectRoomRepository.getProjectListLiveData().getValue(),
                    taskList,
                    mSortMethodLiveData.getValue(),
                    mIsTaskNameEmpty.getValue()
                    )
                );
            }
        });

        mTaskModelUiMediatorLiveData.addSource(mSortMethodLiveData, new Observer<SortMethod>() {
            @Override
            public void onChanged(SortMethod sortMethod) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(
                    mProjectRoomRepository.getProjectListLiveData().getValue(),
                    mTaskRepository.getTaskListLiveData().getValue(),
                    sortMethod,
                    mIsTaskNameEmpty.getValue()
                    )
                );
            }
        });

        mTaskModelUiMediatorLiveData.addSource(mIsTaskNameEmpty, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTaskNameEmpty) {
                mTaskModelUiMediatorLiveData.setValue(combineProjectAndTask(
                    mProjectRoomRepository.getProjectListLiveData().getValue(),
                    mTaskRepository.getTaskListLiveData().getValue(),
                    mSortMethodLiveData.getValue(),
                    isTaskNameEmpty
                    )
                );
            }
        });
    }

    private TasksModelUi combineProjectAndTask(
        @Nullable List<Project> projectList,
        @Nullable List<Task> taskList,
        @Nullable SortMethod sortMethod,
        @Nullable Boolean isTaskNameEmpty
    ) {
        List<TaskCellModelUi> taskCellModels = new ArrayList<>();
        boolean isEmptyStateDisplayed;
        @StringRes
        int emptyTaskNameErrorStringRes = 0;

        if (projectList == null || taskList == null) {
            return new TasksModelUi(taskCellModels, true, emptyTaskNameErrorStringRes);
        }

        for (Task task : taskList) {
            for (Project project : projectList) {
                if (project.getId() == task.getProjectId()) {

                    int colorProject = 0;

                    switch (project.getName()){
                        case "Projet Tartampion" :
                            colorProject = ContextCompat.getColor(MainApplication.getInstance(), R.color.project_tartampion);
                            break;
                        case "Projet Lucidia" :
                            colorProject = ContextCompat.getColor(MainApplication.getInstance(), R.color.project_lucidia);
                            break;
                        case "Projet Circus" :
                            colorProject = ContextCompat.getColor(MainApplication.getInstance(), R.color.project_circus);
                            break;
                    }

                    taskCellModels.add(
                            new TaskCellModelUi(
                                    task.getId(),
                                    project.getName(),
                                    task.getMessage(),
                                    task.getCreationTimestamp(),
                                    colorProject)
                            );
                }
            }
        }

        isEmptyStateDisplayed = taskList.size() == 0;

        // TODO loulou don't work
        if (isTaskNameEmpty != null && isTaskNameEmpty) {
            emptyTaskNameErrorStringRes = R.string.empty_task_name;
        }

        if (sortMethod == null) {
            sortMethod = ALPHABETICAL;
        }

        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(taskCellModels, new TaskCellModelUi.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(taskCellModels, new TaskCellModelUi.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(taskCellModels, new TaskCellModelUi.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(taskCellModels, new TaskCellModelUi.TaskOldComparator());
                break;
        }

        return new TasksModelUi(
            taskCellModels,
            isEmptyStateDisplayed,
            emptyTaskNameErrorStringRes
        );
    }

    public void addNewTask(@NonNull String taskName, @Nullable ProjectModelUi projectModelUi) {
        // If a name has not been set
        mIsTaskNameEmpty.setValue(taskName.trim().isEmpty());

        if (taskName.trim().isEmpty()) {
            // Don't add task with empty name
            return;
        }

        // If both project and name of the task have been set
        if (projectModelUi != null) {

            Task task = new Task(
                projectModelUi.getId(),
                taskName, new Date().getTime()
            );

            mTaskRepository.addTask(task);
        }
    }

    public void deleteTask(TaskCellModelUi task) {
        TasksModelUi tasksModelUi = getTasksModelUi();

        tasksModelUi.getTaskCellModels().remove(task);

        mTaskModelUiMediatorLiveData.setValue(tasksModelUi);

        mTaskRepository.deleteTask(task.getId());
    }

    private TasksModelUi getTasksModelUi() {
        TasksModelUi taskModelUi = mTaskModelUiMediatorLiveData.getValue();

        if (taskModelUi == null || taskModelUi.getTaskCellModels() == null) {
            List<TaskCellModelUi> taskCellModelUiList = new ArrayList<>();

            taskModelUi = new TasksModelUi(taskCellModelUiList, true, 0);
        }

        return taskModelUi;
    }

    public void setSortMethod(SortMethod sortMethod) {
        mSortMethodLiveData.setValue(sortMethod);
    }
}
