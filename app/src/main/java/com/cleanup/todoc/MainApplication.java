package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.data.dao.ProjectDao;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;

import java.util.List;

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

        // TODO : Fixer la creation des projet quand l'application se relance après un kill voulu
        // TODO : les projets s'accumulent à chaque fois
        projectDao.insertProject(new Project("Projet Tartampion"));
        projectDao.insertProject(new Project("Projet Lucidia"));
        projectDao.insertProject(new Project("Projet Circus"));

    }
}
