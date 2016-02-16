package apidez.com.doit.activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import apidez.com.doit.R;
import apidez.com.doit.model.Priority;
import apidez.com.doit.view.activity.TodoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static apidez.com.doit.utils.MatcherUtils.clickItemInRecyclerView;
import static apidez.com.doit.utils.MatcherUtils.clickItemInRecyclerViewWithId;
import static apidez.com.doit.utils.MatcherUtils.dueDateHasBeenChosen;
import static apidez.com.doit.utils.MatcherUtils.hasItemCount;
import static apidez.com.doit.utils.MatcherUtils.itemInListNotWithText;
import static apidez.com.doit.utils.MatcherUtils.itemInListWithId;
import static apidez.com.doit.utils.MatcherUtils.itemInListWithPopCheckBox;
import static apidez.com.doit.utils.MatcherUtils.itemInListWithPriority;
import static apidez.com.doit.utils.MatcherUtils.itemInListWithText;
import static apidez.com.doit.utils.MatcherUtils.priorityHasBeenChosen;

/**
 * Created by nongdenchet on 2/15/16.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class TodoActivityTest {

    @Rule
    public ActivityTestRule<TodoActivity> mActivityTestRule =
            new ActivityTestRule<>(TodoActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        mActivityTestRule.launchActivity(new Intent());
        Espresso.registerIdlingResources(mActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void testNumberOfTodos() throws Exception {
        onView(withId(R.id.todoList)).check(matches(hasItemCount(13))); // Include footer
    }

    @Test
    public void testRenderItem() throws Exception {
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(0, R.id.todoTitle, "Android")));
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(2, R.id.todoSubtitle, "No due date")));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPriority(1, R.id.priority, Priority.LOW)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPopCheckBox(0, R.id.pop_checkbox, false)));
    }

    @Test
    public void testCheckItem() throws Exception {
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(0, R.id.pop_checkbox)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPopCheckBox(0, R.id.pop_checkbox, true)));
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(0, R.id.pop_checkbox)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPopCheckBox(0, R.id.pop_checkbox, false)));
    }

    @Test
    public void testClickItem() throws Exception {
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(0, R.id.todo)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithId(0, R.id.edit_button)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithId(0, R.id.delete_button)));
    }

    @Test
    public void testClickItemDelete() throws Exception {
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerView(0)));
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(0, R.id.delete_button)));
        Thread.sleep(500);
        onView(withId(R.id.todoList)).check(matches(itemInListNotWithText(0, R.id.todoTitle, "Android")));
    }

    @Test
    public void testClickItemUpdate() throws Exception {
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(1, R.id.todo)));
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(1, R.id.edit_button)));
        onView(withText("iOS")).check(matches(isDisplayed()));
        onView(withId(R.id.due_date_picker)).check(matches(dueDateHasBeenChosen(R.id.tomorrow)));
        onView(withId(R.id.priority_picker)).check(matches(priorityHasBeenChosen(Priority.LOW)));
    }

    @Test
    public void updateItem() throws Exception {
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(1, R.id.todo)));
        onView(withId(R.id.todoList)).check(matches(clickItemInRecyclerViewWithId(1, R.id.edit_button)));

        // Edit
        onView(withText("HIGH")).perform(click());
        onView(withText("deal date")).perform(click());
        onView(withText("iOS")).perform(typeText(" developer"));
        onView(withText("SAVE")).perform(click());

        // After edit
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(1, R.id.todoTitle, "iOS developer")));
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(1, R.id.todoSubtitle, "No due date")));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPriority(1, R.id.priority, Priority.HIGH)));
    }

    @Test
    public void testAddItem() throws Exception {
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.title_edit_text)).perform(typeText("Coder"));
        onView(withText("deal date")).perform(click());
        onView(withText("MED")).perform(click());
        onView(withText("SAVE")).perform(click());

        // After add
        Thread.sleep(500);
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(0, R.id.todoTitle, "Coder")));
        onView(withId(R.id.todoList)).check(matches(itemInListWithPriority(0, R.id.priority, Priority.MED)));
        onView(withId(R.id.todoList)).check(matches(itemInListWithText(0, R.id.todoSubtitle, "No due date")));
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(mActivityTestRule.getActivity().getCountingIdlingResource());
    }
}