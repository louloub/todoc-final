package com.cleanup.todoc;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeTaskSource {

    private static FakeTaskSource INSTANCE;

    private static final Map<String, Task> NOTES_SERVICE_DATA = new LinkedHashMap<>();

    private static String FAKE_TASK_TITLE_1 = "FAKE TITLE 1";
    private static int FAKE_TASK_ID_1 = 1;

    public static String FAKE_NOTE_UPDATED_TITLE = "FAKE NOTE UPDATED TITLE";

    private FakeTaskSource() {}

    public static FakeTaskSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTaskSource();
        }
        return INSTANCE;
    }

    public LiveData<List<Task>> getProject() {
        return null;
    }

    public LiveData<Task> getNote(@NonNull String taskId) {
        return null;
    }

    public void saveTask(@NonNull Task task) {
        NOTES_SERVICE_DATA.put(String.valueOf(task.getId()), task);
    }

    public void updateTask(@NonNull Task task) {
        NOTES_SERVICE_DATA.put(String.valueOf(task.getId()), task);
    }

    public void deleteAllProject() {
        NOTES_SERVICE_DATA.clear();
    }

    public void deleteProject(@NonNull Task task) {

    }

    @VisibleForTesting
    public void addNotes(Task... tasks) {
        if (tasks != null) {
            for (Task task : tasks) {
                NOTES_SERVICE_DATA.put(String.valueOf(task.getId()), task);
            }
        }
    }

    public static Task createAndFetchFakeTask(String title) {
        return new Task(FAKE_TASK_ID_1,title,new Date().getTime());
    }

    public static Task fetchFakeTask() {
        return new Task(FAKE_TASK_ID_1, FAKE_TASK_TITLE_1, System.currentTimeMillis());
    }

    public static List<Task> getFakeNotes(int size) {
        List<Task> noteList = new ArrayList<Task>();
        for(int i = 1; i <= size; i++ ) {
            Task task = new Task(i,"task" +i, new Date().getTime());
            noteList.add(task);
        }
        return noteList;
    }
}