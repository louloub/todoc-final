package com.cleanup.todoc.data.model;

import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "projectId",
                onDelete = ForeignKey.CASCADE
        )
)

public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private final int projectId;

    private final String message;

    private final long creationTimestamp;

    public Task(int projectId, String message, long creationTimestamp) {
        this.projectId = projectId;
        this.message = message;
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getProjectId() {
        return projectId;
    }

    @VisibleForTesting
    public void setId(int id) {
        this.id = id;
    }
}