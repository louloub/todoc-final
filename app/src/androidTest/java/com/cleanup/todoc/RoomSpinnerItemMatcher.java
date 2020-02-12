package com.cleanup.todoc;

import androidx.annotation.NonNull;

import com.cleanup.todoc.model.ProjectModelUi;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RoomSpinnerItemMatcher extends TypeSafeMatcher<ProjectModelUi> {
    private final ProjectModelUi projectModelUi;

    public RoomSpinnerItemMatcher(@NonNull ProjectModelUi projectModelUi) {
        this.projectModelUi = projectModelUi;
    }

    @Override
    protected boolean matchesSafely(ProjectModelUi projectModelUi) {
        return this.projectModelUi == projectModelUi;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("RoomSpinnerItemMatcher with Room = " + projectModelUi);
    }
}
