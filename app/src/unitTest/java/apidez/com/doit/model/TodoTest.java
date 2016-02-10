package apidez.com.doit.model;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by nongdenchet on 2/2/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class TodoTest {
    private Todo mTodo;
    private long milliseconds = (24L * 60 * 60 * 1000) * 1000;

    @Before
    public void setUp() throws Exception {
        mTodo = new Todo.Builder("title", Priority.LOW)
                .note("note")
                .completed(true)
                .dueDate(new Date(milliseconds))
                .build();
    }

    @Test
    public void testGetId() throws Exception {
        assertNotNull(mTodo.getId());
    }

    @Test
    public void testGetPriority() throws Exception {
        assertEquals(Priority.LOW, mTodo.getPriority());
    }

    @Test
    public void testSetPriority() throws Exception {
        mTodo.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, mTodo.getPriority());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title", mTodo.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        mTodo.setTitle("new title");
        assertEquals("new title", mTodo.getTitle());
    }

    @Test
    public void testGetNote() throws Exception {
        assertEquals("note", mTodo.getNote());
    }

    @Test
    public void testSetNote() throws Exception {
        mTodo.setNote("new note");
        assertEquals("new note", mTodo.getNote());
    }

    @Test
    public void testGetDueDate() throws Exception {
        assertEquals(milliseconds, mTodo.getDueDate().getTime());
    }

    @Test
    public void testSetDueDate() throws Exception {
        mTodo.setDueDate(new Date(10000));
        assertEquals(10000, mTodo.getDueDate().getTime());
    }

    @Test
    public void testIsCompleted() throws Exception {
        assertTrue(mTodo.isCompleted());
    }

    @Test
    public void testSetCompleted() throws Exception {
        mTodo.setCompleted(false);
        assertFalse(mTodo.isCompleted());
    }

    @Test
    public void testDefaultCompleted() throws Exception {
        mTodo = new Todo.Builder("title", Priority.MED)
                .build();
        assertFalse(mTodo.isCompleted());
    }
}