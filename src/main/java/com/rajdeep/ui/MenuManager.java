package com.rajdeep.ui;

import java.util.Scanner;

public class MenuManager {

    private Scanner scanner = new Scanner(System.in);

    public int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                int v = Integer.parseInt(scanner.nextLine());
                if (v < min || v > max) {
                    System.out.println("Enter number between " + min + " and " + max);
                    continue;
                }
                return v;
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    public String askString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }
}
