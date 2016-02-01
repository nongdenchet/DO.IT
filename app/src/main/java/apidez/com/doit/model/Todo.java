package apidez.com.doit.model;

import java.util.Date;

/**
 * Created by nongdenchet on 2/2/16.
 */
public class Todo {
    private Priority priority;
    private String title;
    private String note;
    private Date dueDate;
    private boolean completed;

    private Todo(Priority priority, String title, String note, Date dueDate, boolean completed) {
        this.priority = priority;
        this.title = title;
        this.note = note;
        this.dueDate = dueDate;
        this.completed = completed;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        private String note;
        private Date dueDate;
        private boolean completed;

        public Builder(String title, Priority priority) {
            this.title = title;
            this.priority = priority;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
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
            return new Todo(priority, title, note, dueDate, completed);
        }
    }
}
