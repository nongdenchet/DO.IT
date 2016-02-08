package apidez.com.doit.utils;

/**
 * Created by nongdenchet on 2/8/16.
 */

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Credit to: <a href="http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string"/>
 */
public final class StringUtils {
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}
