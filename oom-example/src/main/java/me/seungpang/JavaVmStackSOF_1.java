package me.seungpang;

/**
 * VM 매개 변수: -Xss180k
 */
public class JavaVmStackSOF_1 {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVmStackSOF_1 oom = new JavaVmStackSOF_1();
        try {
            oom.stackLeak();
        } catch (Exception e) {
            System.out.println("oom.stackLength = " + oom.stackLength);
            throw e;
        }
    }
}
