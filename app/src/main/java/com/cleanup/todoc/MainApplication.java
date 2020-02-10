package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;

public class MainApplication extends Application {

    private static Application sApplication;

    public MainApplication() {
        sApplication = this;
    }

    public static Application getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ProjectDao projectDao = AppDatabase.getInstance().projectDao();

        projectDao.insertProject(new Project("Projet Tartampion"));
        projectDao.insertProject(new Project("Projet Lucidia"));
        projectDao.insertProject(new Project("Projet Circus"));
    }
}
