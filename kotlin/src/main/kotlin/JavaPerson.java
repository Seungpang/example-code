package main.kotlin;

public class JavaPerson {

    private final String name;
    private int age;

    public JavaPerson(final String name, final int age) {
        if (this.age <= 0) {
            throw new IllegalArgumentException(String.format("나이는 %s일 수 없습니다."));
        }
        this.name = name;
        this.age = age;
    }

    public JavaPerson(String name) {
        this(name, 1);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }
}
