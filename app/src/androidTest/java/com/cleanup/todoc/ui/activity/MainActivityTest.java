package com.cleanup.todoc.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.AndroidTestUtil;
import com.cleanup.todoc.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.AndroidTestUtil.atPosition;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // 1
    @Test
    public void should_two_task_are_display_after_add_three_task_and_delete_one_task(){
        // GIVEN
        createTask("tache 1","Projet Tartampion");
        createTask("tache 2","Projet Lucidia");
        createTask("tache 3","Projet Circus");

        // WHEN
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        // THEN
        onView(withId(R.id.list_tasks)).check(new AndroidTestUtil.RecyclerViewItemCountAssertion(2));
    }

    // 2
    @Test
    public void should_task_are_good_sorting_with_old_comparator() {
        // GIVEN
        createTask("tache 1","Projet Tartampion");

        // WHEN
        // FILTER "A -> Z"
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Les plus anciens d’abord"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // THEN
        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(0, withText("tache 2"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(1, withText("tache 3"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(2, withText("tache 1"))));
    }

    // 3
    @Test
    public void should_task_are_good_sorting_with_a_z_comparator() {
        // WHEN
        // FILTER "A -> Z"
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("A -> Z"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // THEN
        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(0, withText("tache 1"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(1, withText("tache 2"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(2, withText("tache 3"))));
    }

    // 4
    @Test
    public void should_task_are_good_sorting_with_z_a_comparator() {
        // WHEN
        // FILTER "Z -> A"
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Z -> A"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // THEN
        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(0, withText("tache 3"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(1, withText("tache 2"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(2, withText("tache 1"))));
    }

    // 5
    @Test
    public void should_task_are_good_sorting_with_recent_comparator() {
        // WHEN
        // FILTER "A -> Z"
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_filter), withContentDescription("Filter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Les plus récents d’abord"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // THEN
        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(0, withText("tache 1"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(1, withText("tache 3"))));

        onView(withId(R.id.list_tasks))
                .check(matches(atPosition(2, withText("tache 2"))));
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

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
