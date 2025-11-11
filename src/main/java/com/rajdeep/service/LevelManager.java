package com.rajdeep.service;

import com.rajdeep.model.BaseCharacter;

/**
 * Simple leveling logic:
 * - levelUp increases level by 1 (max 5)
 * - applyLevelStats recomputes derived values (example multiplier)
 */
public class LevelManager {

    public void levelUp(BaseCharacter character) {
        if (character.getLevel() < 5) {
            character.setLevel(character.getLevel() + 1);
            applyLevelStats(character);
            System.out.println(character.getName() + " leveled up to " + character.getLevel());
        } else {
            System.out.println(character.getName() + " is already max level.");
        }
    }

    public void applyLevelStats(BaseCharacter character) {
        double mult = character.levelMultiplier();
        int newMax = (int) Math.round(character.getMaxHealth() * mult / (character.levelMultiplier() / (1.0 + 0.2*(character.getLevel()-1))));
        // To avoid compounding repeatedly, we just scale base stats from base power/maxHealth.
        // For simplicity assume stored power/maxHealth are base (level 1). If not, you may need to store baseStats separately.
        character.setCurrentHealth((int) Math.round(character.getCurrentHealth() * (1.0 + 0.2)));
        // Here we just multiply power and maxHealth by 1.2 for each level up; you can refine later.
        // Note: in a real implementation store basePower/baseHealth and compute derived each time.
    }
}
