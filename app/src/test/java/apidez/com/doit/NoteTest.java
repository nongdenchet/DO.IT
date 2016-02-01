package apidez.com.doit;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nongdenchet on 2/1/16.
 */
public class NoteTest {
    private Note mNote;
    private Note mSubNote;

    @Before
    public void setUp() throws Exception {
        // Sub Note
        mSubNote = ImmutableNote.builder()
                .title("quan")
                .content("vu")
                .build();
        // Note
        mNote = ImmutableNote.builder()
                .title("This is a title")
                .content("This is a content")
                .addSubNotes(mSubNote)
                .build();
    }

    @Test
    public void testTitle() throws Exception {
        assertEquals("quan", mSubNote.title());
        assertEquals("This is a title", mNote.title());
    }

    @Test
    public void testContent() throws Exception {
        assertEquals("vu", mSubNote.content());
        assertEquals("This is a content", mNote.content());
    }

    @Test
    public void testSubNotes() throws Exception {
        assertThat(mNote.subNotes()).hasSize(1);
        assertEquals(mSubNote, mNote.subNotes().get(0));
        assertThat(mSubNote.subNotes()).hasSize(0);
    }
}