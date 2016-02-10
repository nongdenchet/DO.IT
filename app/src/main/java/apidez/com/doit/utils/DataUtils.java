package apidez.com.doit.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class DataUtils {

    public static List<Todo> provideMockTodoList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo.Builder("Android", Priority.HIGH).dueDate(new Date()).build());
        todoList.add(new Todo.Builder("iOS", Priority.MED).dueDate(new Date()).build());
        todoList.add(new Todo.Builder("Ruby on Rails", Priority.LOW).dueDate(new Date()).build());
        return todoList;
    }

    public static List<Todo> provideLongMockTodoList() {
        List<Todo> todoList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            todoList.addAll(provideMockTodoList());
        }
        return todoList;
    }

    public static List<Todo> provideEmptyList() {
        return new ArrayList<>();
    }
}
