package com.cleanup.todoc;

import android.app.Application;
import android.database.Cursor;

import androidx.core.content.ContextCompat;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.model.ProjectModelUi;

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

        createProjectOnDataBase();
    }

    public static ProjectModelUi[] getAllProjects() {
        return new ProjectModelUi[]{
                new ProjectModelUi(1, "Projet Tartampion", ContextCompat.getColor(MainApplication.getInstance(), R.color.project_tartampion)),
                new ProjectModelUi(2, "Projet Lucidia", ContextCompat.getColor(MainApplication.getInstance(), R.color.project_lucidia)),
                new ProjectModelUi(3, "Projet Circus", ContextCompat.getColor(MainApplication.getInstance(), R.color.project_circus)),
        };
    }

    public void createProjectOnDataBase(){

        AppDatabase database = AppDatabase.getInstance();

        Cursor mCursor = database.query("SELECT * FROM Project",null);

        if (!mCursor.moveToFirst())
        {
            ProjectDao projectDao = database.projectDao();

            projectDao.insertProject(new Project("Projet Tartampion"));
            projectDao.insertProject(new Project("Projet Lucidia"));
            projectDao.insertProject(new Project("Projet Circus"));
        } else {

        }
    }
}
