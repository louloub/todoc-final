package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TaskCellModelUi {
    /**
     * The unique identifier of the task
     */
    private long id;

    /**
     * The unique identifier of the project associated to the task
     */
    // TODO LOULOUB PAS PERTINENT : IL FAUT METTRE DIRECTEMENT LE NOM DU PROJET EN STRING
    private int projectId;

    // TODO LOULOUB IL MANQUE LE NOM DU PROJECT (EN STRING, C'EST LE VIEWMODELQUIN DOIT CALCULER CA)

    // TODO LOULOUB IL MANQUE LA COULEUR (EN COLOR INT OU COLOR RES, QU'IMPORTE)

    private int color;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    /**
     * Instantiates a new TaskCellModelUi.
     *
     * @param id                the unique identifier of the task to set
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     * @param color             the color of project
     */
    public TaskCellModelUi(
            long id,
            int projectId,
            @NonNull String name,
            long creationTimestamp,
            int color)
    {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
        this.setColor(color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique idenifier of the task to set
     */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param projectId the unique identifier of the project associated to the task to set
     */
    private void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    /*@Nullable
    public String getProject() {
        return ProjectModelUi.getName();
    }*/

    @Nullable
    public ProjectModelUi getProject() {
        return ProjectModelUi.getProjectById(projectId);
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    private void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<TaskCellModelUi> {
        @Override
        public int compare(TaskCellModelUi left, TaskCellModelUi right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<TaskCellModelUi> {
        @Override
        public int compare(TaskCellModelUi left, TaskCellModelUi right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<TaskCellModelUi> {
        @Override
        public int compare(TaskCellModelUi left, TaskCellModelUi right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<TaskCellModelUi> {
        @Override
        public int compare(TaskCellModelUi left, TaskCellModelUi right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
