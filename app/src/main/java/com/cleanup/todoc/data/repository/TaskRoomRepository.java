package com.cleanup.todoc.data.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.dao.TaskDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Task;

import java.util.List;

public class TaskRoomRepository implements TaskRepository {

    private final TaskDao mTaskDao;
    private final LiveData<List<Task>> mTaskListLiveData;

    public TaskRoomRepository() {
        AppDatabase db = AppDatabase.getInstance();
        mTaskDao = db.taskDao();
        mTaskListLiveData = mTaskDao.getListTaskLiveData();
    }

    @Override
    public LiveData<List<Task>> getTaskListLiveData() {
        return mTaskListLiveData;
    }

    @Override
    public void addTask(Task task) {

        new InsertAsyncTask(mTaskDao).execute(task);
    }

    @Override
    public void deleteTask(long taskId) {

        mTaskDao.deleteTask(taskId);
    }

    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        private final TaskDao mAsyncTaskDao;

        InsertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insertTask(params[0]);
            return null;
        }
    }
}
