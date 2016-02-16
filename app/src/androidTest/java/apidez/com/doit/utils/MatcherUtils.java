package apidez.com.doit.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import apidez.com.doit.R;
import apidez.com.doit.model.Priority;
import apidez.com.doit.view.custom.DueDatePicker;
import apidez.com.doit.view.custom.DueDateView;
import apidez.com.doit.view.custom.PopCheckBox;
import apidez.com.doit.view.custom.PriorityPicker;
import apidez.com.doit.view.custom.PriorityView;
import rx.schedulers.Schedulers;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class MatcherUtils {

    /**
     * Check item in recyclerview with text
     */
    public static Matcher<View> itemInListWithId(final int position, final int id) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    View target = holder.itemView.findViewById(id);
                    return target != null;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in list with id");
            }
        };
    }

    /**
     * Check item in recyclerview with text
     */
    public static Matcher<View> itemInListWithText(final int position, final int id, final String text) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    TextView textView = ((TextView) holder.itemView.findViewById(id));
                    return TextUtils.equals(textView.getText(), text) && textView.getVisibility() == View.VISIBLE;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in list with text");
            }
        };
    }

    /**
     * Check item in recyclerview not with text
     */
    public static Matcher<View> itemInListNotWithText(final int position, final int id, final String text) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    TextView textView = ((TextView) holder.itemView.findViewById(id));
                    return !TextUtils.equals(textView.getText(), text) && textView.getVisibility() == View.VISIBLE;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in list not with text");
            }
        };
    }

    /**
     * Check item background color
     */
    public static Matcher<View> dueDateHasBeenChosen(final int dueDateId) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    DueDatePicker dueDatePicker = (DueDatePicker) view;
                    DueDateView dueDateView = (DueDateView) dueDatePicker.findViewById(dueDateId);
                    final boolean[] match = {false};
                    dueDatePicker.date().subscribeOn(Schedulers.immediate()).subscribe(date -> {
                        match[0] = DateUtils.sameDate(date, dueDateView.getDate());
                    });
                    return match[0];
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("due date has been chosen");
            }
        };
    }

    /**
     * Check item background color
     */
    public static Matcher<View> priorityHasBeenChosen(Priority newPriority) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    PriorityPicker priorityPicker = (PriorityPicker) view;
                    final boolean[] match = {false};
                    priorityPicker.priority().subscribe(priority -> {
                        match[0] = (priority == newPriority);
                    });
                    return match[0];
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("priority has been chosen");
            }
        };
    }

    /**
     * Check item in recyclerview with priority
     */
    public static Matcher<View> itemInListWithPriority(final int position, final int id, final Priority priority) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    PriorityView priorityView = ((PriorityView) holder.itemView.findViewById(id));
                    return priority == priorityView.getPriority();
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in list with priority");
            }
        };
    }

    /**
     * Check item in recyclerview with checkbox
     */
    public static Matcher<View> itemInListWithPopCheckBox(final int position, final int id, final boolean isChecked) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    PopCheckBox checkBox = ((PopCheckBox) holder.itemView.findViewById(id));
                    View todo = holder.itemView.findViewById(R.id.todo);
                    boolean rightAlpha = (isChecked && todo.getAlpha() == 0.5f) || (!isChecked && todo.getAlpha() == 1.0f);
                    return isChecked == checkBox.isChecked() && rightAlpha && checkBox.getVisibility() == View.VISIBLE;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in list with pop_check_box");
            }
        };
    }

    /**
     * Click item in recyclerview
     */
    public static Matcher<View> clickItemInRecyclerView(final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    holder.itemView.performClick();
                    return true;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("click item in list");
            }
        };
    }

    /**
     * Click item with id in recyclerview
     */
    public static Matcher<View> clickItemInRecyclerViewWithId(final int position, final int id) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                try {
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
                    holder.itemView.findViewById(id).performClick();
                    return true;
                } catch (Exception exception) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("click item in list with id");
            }
        };
    }

    /**
     * Like text
     */
    public static Matcher<View> withTextLike(final String text) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text: ");
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                return textView.getText().toString().contains(text);
            }
        };
    }

    /**
     * Returns a matcher that matches {@link RecyclerView}s has exactly item counts
     */
    public static Matcher<View> hasItemCount(final int count) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has items");
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean matchesSafely(View view) {
                try {
                    return ((RecyclerView) view).getAdapter().getItemCount() == count;
                } catch (Exception exception) {
                    return false;
                }
            }
        };
    }
}
