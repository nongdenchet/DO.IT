package apidez.com.doit.model;

import java.util.Date;

import apidez.com.doit.utils.StringUtils;

/**
 * Created by nongdenchet on 2/2/16.
 */
public class Todo {
    private String id;
    private Priority priority;
    private String title;
    private Date dueDate;
    private boolean completed;

    private Todo(String id, String title, Date dueDate, boolean completed, Priority priority) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.completed = completed;
        this.priority = priority;
    }

    public String getId() {
        return id;
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

        private String generateId() {
            return StringUtils.nextSessionId();
        }

        public Todo build() {
            return new Todo(generateId(), title, dueDate, completed, priority);
        }
    }
}
