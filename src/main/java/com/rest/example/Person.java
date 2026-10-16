package com.rest.example;

class Person {
    private final String name;
    private final int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    boolean isAdult() { return age >= 18; }

    String greeting() {
        return String.format("Hello, %s! You are %d years old and %s.", name, age,
                isAdult() ? "an adult" : "a minor");
    }
}