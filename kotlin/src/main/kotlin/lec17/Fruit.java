package main.kotlin.lec17;

public class Fruit {

    private final String name;
    private final int price;

    public Fruit(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
