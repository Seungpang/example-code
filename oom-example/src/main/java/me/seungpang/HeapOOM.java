package me.seungpang;

import java.util.ArrayList;
import java.util.List;

/**
 * VM 매개 변수: -Xms20m - Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
