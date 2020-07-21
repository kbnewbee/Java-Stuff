package com.company;

public class Pet {
    protected final String name;
    private PrimaryColor color;

    public void setColor(PrimaryColor color) {
        this.color = color;
    }

    public PrimaryColor getColor() {
        return color;
    }

    public Pet(String name) {
        this.name = name;
    }

    public void feed() {
        System.out.println("I am feeding " + getName());
    }

    public void feed(String food) {
        System.out.println("I am feeding " + food + getName());

    }

    public String getName() {
        return name;
    }
}
