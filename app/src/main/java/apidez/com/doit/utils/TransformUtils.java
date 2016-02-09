package apidez.com.doit.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nongdenchet on 1/11/16.
 */
public class TransformUtils {

    public interface Predicate<T> {
        Boolean condition(T item);
    }

    public interface Map<T> {
        <E> E map(T item);
    }

    /**
     * filter a collection
     * @param items : input collection
     * @param predicate : condition
     */
    public static <T> Collection<T> filter(Collection<T> items, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.condition(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * map a collection to another collection
     * @param items : input collection
     * @param mapper : mapper
     */
    public static <T, E> Collection<E> map(Collection<T> items, Map<T> mapper) {
        Collection<E> result = new ArrayList<>();
        for (T item : items) {
            result.add(mapper.map(item));
        }
        return result;
    }
}
