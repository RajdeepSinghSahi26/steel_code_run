package com.rajdeep.app;

import com.rajdeep.controller.GameController;

public class MainApp {
    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.start();   // runs simple demo flow
    }
}
