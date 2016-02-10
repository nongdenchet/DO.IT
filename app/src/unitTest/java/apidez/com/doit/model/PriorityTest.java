package apidez.com.doit.model;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by nongdenchet on 2/9/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PriorityTest {

    @Test
    public void testHighOrdinal() throws Exception {
        assertEquals(2, Priority.HIGH.ordinal());
    }

    @Test
    public void testValueOf() throws Exception {
        assertEquals(Priority.HIGH, Priority.valueOf("HIGH"));
    }

    @Test
    public void testValues() throws Exception {
        assertThat(Priority.values()).contains(Priority.LOW, Priority.MED, Priority.HIGH);
    }

    @Test
    public void testMedOrdinal() throws Exception {
        assertEquals(1, Priority.MED.ordinal());
    }

    @Test
    public void testLowOrdinal() throws Exception {
        assertEquals(0, Priority.LOW.ordinal());
    }
}