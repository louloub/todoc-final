package com.cleanup.todoc.model;

import androidx.annotation.StringRes;

import java.util.List;

public class TasksModelUi {

    private final List<TaskCellModelUi> taskCellModels;

    private final boolean isEmptyStateDisplayed;

    @StringRes
    private final int emptyTaskNameErrorStringRes;

    public TasksModelUi(
        List<TaskCellModelUi> taskCellModels,
        boolean isEmptyStateDisplayed,
        @StringRes int emptyTaskNameErrorStringRes
    ) {
        this.taskCellModels = taskCellModels;
        this.isEmptyStateDisplayed = isEmptyStateDisplayed;
        this.emptyTaskNameErrorStringRes = emptyTaskNameErrorStringRes;
    }

    public List<TaskCellModelUi> getTaskCellModels() {
        return taskCellModels;
    }

    public boolean isEmptyStateDisplayed() {
        return isEmptyStateDisplayed;
    }

    @StringRes
    public int getEmptyTaskNameErrorStringRes() {
        return emptyTaskNameErrorStringRes;
    }
}
