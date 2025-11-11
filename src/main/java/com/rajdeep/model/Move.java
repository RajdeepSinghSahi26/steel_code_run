package com.rajdeep.model;

public class Move {
    private String name;
    private int damage;
    private int cooldown;      // in turns
    private int maxUses;
    private int currentUses;
    private boolean special;   // e.g., time stop

    public Move() {}

    public Move(String name, int damage, int cooldown, int maxUses, boolean special) {
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.maxUses = maxUses;
        this.currentUses = 0;
        this.special = special;
    }

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    public int getMaxUses() { return maxUses; }
    public void setMaxUses(int maxUses) { this.maxUses = maxUses; }
    public int getCurrentUses() { return currentUses; }
    public void setCurrentUses(int currentUses) { this.currentUses = currentUses; }
    public boolean isSpecial() { return special; }
    public void setSpecial(boolean special) { this.special = special; }

    public boolean canUse() {
        return currentUses < maxUses;
    }

    public void used() {
        this.currentUses++;
    }

    public void resetForBattle() {
        this.currentUses = 0;
    }
}
