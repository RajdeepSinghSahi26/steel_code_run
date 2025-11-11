package com.rajdeep.ui;

public class DisplayManager {

    public static void line() {
        System.out.println("--------------------------------------------------");
    }

    public static void title(String t) {
        line();
        System.out.println("  " + t);
        line();
    }

    public static void info(String s) {
        System.out.println("[i] " + s);
    }

    public static void warn(String s) {
        System.out.println("[!] " + s);
    }
}
