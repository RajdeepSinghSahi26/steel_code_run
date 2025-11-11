package com.rajdeep.model;

import java.util.List;

public abstract class BaseCharacter {
    protected int id;               // DB id
    protected String name;
    protected int level;           // 1..5
    protected int maxHealth;
    protected int currentHealth;
    protected int power;           // attack strength
    protected int defense;
    protected int speed;
    protected List<Move> moves;

    public BaseCharacter() {}

    // Basic getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth; }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public List<Move> getMoves() { return moves; }
    public void setMoves(List<Move> moves) { this.moves = moves; }

    public void resetMovesForBattle() {
        if (moves == null) return;
        for (Move m : moves) m.resetForBattle();
    }

    // Derived effective stat (applies level multiplier)
    public double levelMultiplier() {
        // level 1 = 1.0, each level increases by 20%: 1 + 0.2*(level-1)
        return 1.0 + 0.2 * Math.max(0, level - 1);
    }

    public int effectivePower() {
        return (int) Math.round(power * levelMultiplier());
    }

    public int effectiveMaxHealth() {
        return (int) Math.round(maxHealth * levelMultiplier());
    }
}
