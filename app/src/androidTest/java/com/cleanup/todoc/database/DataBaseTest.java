package com.cleanup.todoc.database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.database.Cursor;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import androidx.room.Room;

import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.R;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.ui.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.ui.activity.MainActivitySortingTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@LargeTest
public class DataBaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private AppDatabase mDatabase;

    @Before
    public void initDb() {
        mDatabase = Room.databaseBuilder(
            MainApplication.getInstance(),
            AppDatabase.class,
            "Database.db"
        ).allowMainThreadQueries()
            .build();
    }

    // 1
    @Test
    public void should_three_task_are_in_db_after_add_three_task(){
        // GIVEN
        createTask("tache 1");
        createTask("tache 2");
        createTask("tache 3");

        // WHEN
        Cursor mCursor = mDatabase.query("SELECT * FROM Task",null);

        // THEN
        assertEquals(3, mCursor.getCount());
    }

    // 2
    @Test
    public void should_two_task_are_in_db_after_add_three_task_and_delete_one(){
        // GIVEN
        ViewInteraction appCompatImageView = onView(
            allOf(withId(R.id.img_delete),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.list_tasks),
                        0),
                    1),
                isDisplayed()));
        appCompatImageView.perform(click());

        // WHEN
        Cursor mCursor = mDatabase.query("SELECT * FROM Task",null);

        // THEN
        assertEquals(2, mCursor.getCount());
    }

    private void createTask(String taskName) {
        // Click on "add tack button"
        ViewInteraction floatingActionButton = onView(
            allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    2),
                isDisplayed()));
        floatingActionButton.perform(click());

        // Set task name
        ViewInteraction appCompatEditText = onView(
            allOf(withId(R.id.txt_task_name),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.custom),
                        0),
                    0),
                isDisplayed()));
        appCompatEditText.perform(replaceText(taskName), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
            allOf(withId(android.R.id.button1), withText("Ajouter"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0),
                    3)));
        appCompatButton.perform(scrollTo(), click());
    }
}