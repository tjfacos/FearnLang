package util;

import static org.assertj.core.api.Assertions.*;

public class Helper {
    public static Boolean equal(Object a, Object b)
    {

        try {
            assertThat(a).usingRecursiveComparison().isEqualTo(b);
        } catch (AssertionError e) {
            return false;
        }

        return true;
    }
}
