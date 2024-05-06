package me.seungpang;

import java.util.HashSet;
import java.util.Set;

/**
 * JDK7이하 VM 매개 변수: --XX:PermSize=6M -XX:MaxPermSize=6M
 * JDK8이상 VM 매개 변수: --XX:MetaspaceSize=6M -XX:MaxMetaspaceSize=6M
 */
public class RuntimeConstantPoolOOM_1 {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        short i = 0;

        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
