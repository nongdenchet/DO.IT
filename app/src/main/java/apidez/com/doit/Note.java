package apidez.com.doit;

import org.immutables.value.Value;

import java.util.List;

/**
 * Created by nongdenchet on 2/1/16.
 */
@Value.Immutable
public abstract class Note {
    public abstract String title();
    public abstract String content();
    public abstract List<Note> subNotes();
}
