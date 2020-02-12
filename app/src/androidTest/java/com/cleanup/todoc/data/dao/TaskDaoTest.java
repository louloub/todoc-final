package com.cleanup.todoc.data.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.cleanup.todoc.AndroidTestUtil;
import com.cleanup.todoc.FakeTaskSource;
import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.R;
import com.cleanup.todoc.data.dataBase.AppDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.model.ProjectModelUi;
import com.cleanup.todoc.ui.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.ui.activity.MainActivityTest.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@LargeTest
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private AppDatabase mDatabase;

    @Before
    public void initDb() throws Exception {
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
        createTask("tache 1","Projet Tartampion");
        createTask("tache 2","Projet Lucidia");
        createTask("tache 3","Projet Circus");

        // WHEN
        Cursor mCursor = mDatabase.query("SELECT * FROM Task",null);

        // THEN
        assertEquals(3, mCursor.getCount());
    }

    private void createTask(String taskName, String project) {
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

        /*// Click on spinner
        onView(withId(R.id.project_spinner)).perform(click());

        onData(new TypeSafeMatcher<String>(project) {}).perform(click());

        // Choose project in spinner list
        *//*ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.project_spinner), withContentDescription(project),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());*//*

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.project_spinner), withContentDescription(project),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());*/

        //onView(withId(R.id.project_spinner)).perform(click());

        /*onView(withId(R.id.project_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());*/

        //Espresso.onData(new RoomSpinnerItemMatcher(room)).perform(ViewActions.scrollTo(), ViewActions.click());

        /*onData(allOf(is(instanceOf(String.class)),
                is(project))).inRoot(isPlatformPopup()).perform(click());*/

        /*ViewInteraction customTextView = onView(
                allOf(withId(R.id.project_spinner), withText("Project Tartampion"), isDisplayed()));
        customTextView.perform(click());*/

        /*DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());*/


        /*onView(ViewMatchers.withId(R.id.project_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(project))).perform(click());
        onView(withId(R.id.project_spinner)).check(matches(withSpinnerText(containsString(project))));
        */

        /*onData(allOf(is(instanceOf(String.class)), is(project)))
                .perform(click());
        onView(withId(R.id.project_spinner))
                .check(matches(withSpinnerText(containsString(project))));*/

        /*onData(allOf(is(instanceOf(String.class)), is(project)))
                .perform(click());
        onView(withId(R.id.project_spinner))
                .check(matches(withSpinnerText(containsString(project))));
        */

        // onData(allOf(is(instanceOf(String.class)), is(project))).perform(click());
        // Espresso.onData(allOf(is(instanceOf(Integer.class)))).atPosition(1).perform(ViewActions.click());

        // onData(allOf(is(instanceOf(Integer.class)))).atPosition(1).perform(click());

        /*onData(allOf(is(instanceOf(Integer.class)),
                is(1))).inRoot(isPlatformPopup()).perform(click());*/

        // onData(allOf(is(instanceOf(Integer.class)), is(1))).perform(click());

        /*DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());*/

        // onData(allOf(is(instanceOf(Integer.class)))).atPosition(project).perform(click());

        /*Espresso.onView(ViewMatchers.withId(R.id.project_spinner)).perform(ViewActions.click());
        Espresso.onData(allOf(is(instanceOf(Integer.class)))).atPosition(project-1).perform(ViewActions.click());
        */

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