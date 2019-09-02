package com;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class TestOne {
    public static void main(String[] args) throws Exception {
        CharSequence s = "";
        TestOne.print(new PersonOne<>(""));
    }

    static <T> void print(Person<? extends T> person) throws Exception {
        System.out.println(person.call().getClass().getName());
    }

    static class Person<T> implements Callable<T> {

        private T item;

        public Person(T item) {
            this.item = item;
        }

        @Override
        public T call() throws Exception {
            System.out.println("Person类");
            return item;
        }
    }

    static class PersonOne<T> extends Person<T>{

        private T item;

        public PersonOne(T item) {
            super(item);
            this.item = item;
        }

        @Override
        public T call() throws Exception {
            System.out.println("PersonOne类");
            return item;
        }
    }
}
