package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.model.ProjectModelUi;

import java.util.List;
import java.util.Objects;

public class ProjectRoomRepository implements ProjectRepository {

    private LiveData<List<Project>> mProjectListLiveData;
    private static ProjectModelUi[] mProjectModelUi;

    public ProjectRoomRepository() {
        AppDatabase db = AppDatabase.getInstance();
        ProjectDao mProject = db.projectDao();
        mProjectListLiveData = mProject.getListProjectLiveData();

        /*if (mProject.getListProjectLiveData().getValue() != null) {
            mProjectModelUi = (ProjectModelUi[]) Objects.requireNonNull(mProject.getListProjectLiveData().getValue()).toArray();
        }*/
    }

    @Override
    public LiveData<List<Project>> getProjectListLiveData() {
        return mProjectListLiveData;
    }

    public static ProjectModelUi[] getListProjectModelUiLiveData() {
        return mProjectModelUi;
    }
}
