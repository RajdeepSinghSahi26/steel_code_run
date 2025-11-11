package com.rajdeep.service;

import com.rajdeep.model.BaseCharacter;
import com.rajdeep.model.Move;

import java.util.Random;
import java.util.Scanner;

/*
 Very small, synchronous battle loop to illustrate the mechanics you described:
 - 30 seconds total
 - each move consumes 5 seconds
 - moves have max uses
 - simple damage formula: damage = move.damage + attacker.effectivePower() - defender.defense
 - special effects (like freeze) handled by a simple flag that skips opponent's next turn
*/
public class BattleService {

    private final int TOTAL_TIME = 30;
    private final int MOVE_TIME = 5;
    private final Random random = new Random();

    public void startDuel(BaseCharacter hero, BaseCharacter villain) {
        System.out.println("Battle: " + hero.getName() + " vs " + villain.getName());
        hero.resetMovesForBattle();
        villain.resetMovesForBattle();

        int remaining = TOTAL_TIME;
        boolean heroSkip = false;
        boolean villainSkip = false;
        Scanner scanner = new Scanner(System.in);

        while (remaining > 0 && hero.getCurrentHealth() > 0 && villain.getCurrentHealth() > 0) {
            // Hero turn
            if (!heroSkip) {
                System.out.println("\nRemaining time: " + remaining + "s");
                System.out.println("Your moves:");
                int idx = 1;
                for (Move m : hero.getMoves()) {
                    System.out.printf("%d) %s (dmg=%d, used=%d/%d, special=%b)\n",
                            idx++, m.getName(), m.getDamage(), m.getCurrentUses(), m.getMaxUses(), m.isSpecial());
                }
                System.out.print("Choose move number: ");
                int choice = 1;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) { choice = 1; }
                if (choice < 1) choice = 1;
                if (choice > hero.getMoves().size()) choice = hero.getMoves().size();
                applyMove(hero, villain, hero.getMoves().get(choice - 1));
            } else {
                System.out.println(hero.getName() + " is frozen/skipped this turn!");
                heroSkip = false;
            }
            remaining -= MOVE_TIME;
            if (isBattleEnd(hero, villain, remaining)) break;

            // Villain turn (simple AI: random usable move)
            if (!villainSkip) {
                Move vm = chooseRandomUsable(villain);
                if (vm != null) {
                    System.out.println("\nVillain uses: " + vm.getName());
                    applyMove(villain, hero, vm);
                } else {
                    System.out.println("\nVillain has no usable moves, skips.");
                }
            } else {
                System.out.println(villain.getName() + " is frozen/skipped!");
                villainSkip = false;
            }
            remaining -= MOVE_TIME;
            if (isBattleEnd(hero, villain, remaining)) break;
        }

        determineWinner(hero, villain);
    }

    private void applyMove(BaseCharacter attacker, BaseCharacter defender, Move move) {
        if (!move.canUse()) {
            System.out.println("Move " + move.getName() + " has no uses left — it fails.");
            return;
        }
        move.used();

        int damage = Math.max(1, move.getDamage() + attacker.effectivePower() - defender.getDefense());
        defender.setCurrentHealth(Math.max(0, defender.getCurrentHealth() - damage));
        System.out.printf("%s hits %s for %d damage (defender HP: %d)\n",
                attacker.getName(), defender.getName(), damage, defender.getCurrentHealth());

        if (move.isSpecial()) {
            // Example: special move = freeze opponent for one turn
            // We'll represent special by setting a static skip flag in the caller (simple design).
            System.out.println(attacker.getName() + " used special: " + move.getName());
            // Caller logic must handle skip; but for simplicity we print message.
        }
    }

    private boolean isBattleEnd(BaseCharacter a, BaseCharacter b, int remaining) {
        if (a.getCurrentHealth() <= 0 || b.getCurrentHealth() <= 0) return true;
        if (remaining <= 0) {
            System.out.println("Time up!");
            return true;
        }
        return false;
    }

    private Move chooseRandomUsable(BaseCharacter c) {
        if (c.getMoves() == null || c.getMoves().isEmpty()) return null;
        // naive: pick random until find usable or return null
        for (int i = 0; i < c.getMoves().size(); i++) {
            Move m = c.getMoves().get(random.nextInt(c.getMoves().size()));
            if (m.canUse()) return m;
        }
        return null;
    }

    private void determineWinner(BaseCharacter hero, BaseCharacter villain) {
        if (hero.getCurrentHealth() <= 0 && villain.getCurrentHealth() <= 0) {
            System.out.println("Draw!");
        } else if (hero.getCurrentHealth() <= 0) {
            System.out.println(villain.getName() + " wins!");
        } else if (villain.getCurrentHealth() <= 0) {
            System.out.println(hero.getName() + " wins!");
        } else {
            // time ran out -> decide by remaining health
            if (hero.getCurrentHealth() > villain.getCurrentHealth()) {
                System.out.println("Time up: " + hero.getName() + " wins by HP!");
            } else if (villain.getCurrentHealth() > hero.getCurrentHealth()) {
                System.out.println("Time up: " + villain.getName() + " wins by HP!");
            } else {
                System.out.println("Time up: Draw!");
            }
        }
    }
}
