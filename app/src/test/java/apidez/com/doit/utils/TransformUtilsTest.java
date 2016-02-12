package apidez.com.doit.utils;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by nongdenchet on 2/9/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class TransformUtilsTest {

    @Test
    public void testFilter() throws Exception {
        Collection<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> evenData = TransformUtils.filter(data, item -> item % 2 == 0);
        assertEquals(2, evenData.size());
        assertTrue(evenData.contains(2));
        assertTrue(evenData.contains(4));
    }

    @Test
    public void testMap() throws Exception {
        Collection<Integer> data = Arrays.asList(2, 4, 6);
        Collection<Integer> newData = TransformUtils.map(data, new TransformUtils.Map<Integer>() {
            @Override
            public Integer map(Integer item) {
                return item / 2;
            }
        });
        assertEquals(3, newData.size());
        assertTrue(newData.contains(1));
        assertTrue(newData.contains(2));
        assertTrue(newData.contains(3));
    }
}