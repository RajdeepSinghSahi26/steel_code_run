package com.rajdeep.model;

// Simple container that can be used for DB mapping; inherits BaseCharacter
public class Character extends BaseCharacter {
    public Character() {}
    public Character(String name, int level, int maxHealth, int power, int defense, int speed) {
        this.name = name;
        this.level = level;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.power = power;
        this.defense = defense;
        this.speed = speed;
    }
}
