package apidez.com.doit.utils;

import java.util.ArrayList;
import java.util.List;

import apidez.com.doit.model.Priority;
import apidez.com.doit.model.Todo;

/**
 * Created by nongdenchet on 2/8/16.
 */
public class DataUtils {

    public static List<Todo> provideMockTodoList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo.Builder("Android", Priority.HIGH).build());
        todoList.add(new Todo.Builder("iOS", Priority.MEDIUM).build());
        todoList.add(new Todo.Builder("Ruby on Rails", Priority.LOW).build());
        return todoList;
    }
}
