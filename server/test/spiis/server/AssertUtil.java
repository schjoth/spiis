package spiis.server;

import java.util.Collection;

public class AssertUtil {

    public static <T> boolean listsContainSame(Collection<T> a, Collection<T> b) {
        return a.size() == b.size() && a.containsAll(b) && b.containsAll(a);
    }
}
