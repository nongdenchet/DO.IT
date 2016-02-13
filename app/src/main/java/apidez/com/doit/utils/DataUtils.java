package apidez.com.doit.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class DataUtils {
    private static String[] LANGUAGES = new String[]{
            "Android", "iOS", "Ruby on Rails", "C#", "Java", "Swift",
            "Python", "C", "C++", "Objective-C", "Scala", "Perl"
    };

    private static Priority[] PRIORITIES = new Priority[]{
            Priority.HIGH, Priority.LOW, Priority.LOW, Priority.MED, Priority.HIGH, Priority.MED,
            Priority.HIGH, Priority.LOW, Priority.LOW, Priority.MED, Priority.HIGH, Priority.MED
    };

    public static List<Todo> provideMockTodoList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo.Builder("Android", Priority.HIGH).dueDate(new Date()).build());
        todoList.add(new Todo.Builder("iOS", Priority.MED).dueDate(new Date()).build());
        todoList.add(new Todo.Builder("Ruby on Rails", Priority.LOW).dueDate(new Date()).build());
        return todoList;
    }

    public static Todo provideTodo() {
        return new Todo.Builder("title", Priority.HIGH).build();
    }

    public static List<Todo> provideLongMockTodoList() {
        List<Todo> todoList = new ArrayList<>();

        // Three special case
        todoList.add(
                new Todo.Builder(LANGUAGES[0], PRIORITIES[0])
                        .dueDate(new Date())
                        .completed(false)
                        .build()
        );
        todoList.add(
                new Todo.Builder(LANGUAGES[1], PRIORITIES[1])
                        .dueDate(DateUtils.getTomorrow())
                        .completed(false)
                        .build()
        );
        todoList.add(
                new Todo.Builder(LANGUAGES[2], PRIORITIES[2])
                        .completed(false)
                        .build()
        );

        // Others
        Random random = new Random();
        for (int i = 3; i < 12; i++) {
            todoList.add(
                    new Todo.Builder(LANGUAGES[i], PRIORITIES[i])
                            .completed(random.nextBoolean())
                            .dueDate(DateUtils.createRandomDate())
                            .build()
            );
        }

        // add id to them
        for (int i = 0; i < 12; i++) {
            todoList.get(i).setId((long) (i + 1));
        }
        return todoList;
    }

    public static List<Todo> provideEmptyList() {
        return new ArrayList<>();
    }
}
