package com.company;

public class Cat extends Pet implements Furry {

    public String noise;

    public String getNoise() {
        return noise;
    }

    private Cat(String name) {
        super(name);
        noise = "meow meow";
    }

    public static Cat makeConstructor(String name) {
        return new Cat(name);
    }

    @Override
    public void feed() {
        System.out.println("The cat is feeding " + name);
    }

    @Override
    public void groom() {
        System.out.println("I have fur");
    }
}
