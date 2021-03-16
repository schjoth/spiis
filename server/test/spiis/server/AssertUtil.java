package spiis.server;

import org.springframework.lang.Nullable;

import java.util.Collection;

public class AssertUtil {

    public static <T> boolean listsContainSame(@Nullable Collection<T> a, @Nullable Collection<T> b) {
        if (a == null || b == null)
            return a == null && b == null;
        return a.size() == b.size() && a.containsAll(b) && b.containsAll(a);
    }
}
