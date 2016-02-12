package apidez.com.doit.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nongdenchet on 2/2/16.
 */
public class Todo implements Serializable {
    Priority priority;
    String title;
    Date dueDate;
    boolean completed;

    public Todo(String title, Date dueDate, boolean completed, Priority priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.completed = completed;
        this.priority = priority;
    }

    public void switchComplete() {
        completed = !completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return 0L;
    }

    /**
     * Builder class
     */
    public static class Builder {
        private Priority priority;
        private String title;
        private Date dueDate;
        private boolean completed;

        public Builder(String title, Priority priority) {
            this.title = title;
            this.priority = priority;
        }

        public Builder dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Todo build() {
            return new Todo(title, dueDate, completed, priority);
        }
    }
}
