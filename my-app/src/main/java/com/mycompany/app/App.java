package com.mycompany.app;

import com.google.common.base.Function;

public class App {
    public static void main(String[] args) {
        System.out.println(HELLO_FUNCTION.apply("UAM"));
    }
    private final static Function<String, String> HELLO_FUNCTION = new Function<String, String>() {
        public String apply(String name) {
            return "Hello " + name;
        }
    };
}
