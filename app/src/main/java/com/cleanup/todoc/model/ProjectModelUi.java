package com.cleanup.todoc.model;

import androidx.annotation.NonNull;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class ProjectModelUi {
    /**
     * The unique identifier of the project
     */
    private final int id;

    /**
     * The name of the project
     */
    @NonNull
    private final String name;

    /**
     * Instantiates a new ProjectModelUi.
     *
     * @param id    the unique identifier of the project to set
     * @param name  the name of the project to set
     */
    public ProjectModelUi(int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    private String getName() {
        return name;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
