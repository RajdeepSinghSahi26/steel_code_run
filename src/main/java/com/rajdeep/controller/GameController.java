package com.rajdeep.controller;

import com.rajdeep.dao.CharacterDAO;
import com.rajdeep.dao.CharacterDAOImpl;
import com.rajdeep.model.Character;
import com.rajdeep.model.Hero;
import com.rajdeep.model.Move;
import com.rajdeep.model.Villain;
import com.rajdeep.service.BattleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 Simple controller to demo loading characters and starting a battle.
 Extend with MenuManager/UI later.
*/
public class GameController {

    private CharacterDAO characterDAO = new CharacterDAOImpl();
    private BattleService battleService = new BattleService();

    public void start() {
        System.out.println("Starting Steel Code Run (demo)...");

        try {
            // Demo: if DB empty, create two demo characters (only adds if table exists)
            List<Character> all = characterDAO.getAllCharacters();
            if (all == null || all.isEmpty()) {
                System.out.println("No characters found in DB. Creating demo characters (not required).");
                Hero h = new Hero("Jotaro Kujo", 1, 200, 30, 10, 20);
                Villain v = new Villain("Dio", 1, 200, 28, 12, 18);
                characterDAO.addCharacter(h);
                characterDAO.addCharacter(v);
                all = characterDAO.getAllCharacters();
            }

            System.out.println("Characters available:");
            int idx = 1;
            for (Character c : all) {
                System.out.printf("%d) %s (lvl %d)\n", idx++, c.getName(), c.getLevel());
            }

            // For demo: pick first as hero, second as villain if exists
            Character hero = all.get(0);
            Character villain = all.size() > 1 ? all.get(1) : null;

            // attach simple moves for demo
            hero.setMoves(demoMovesForHero());
            if (villain != null) villain.setMoves(demoMovesForVillain());

            System.out.println("Starting demo battle...");
            battleService.startDuel(hero, villain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Move> demoMovesForHero() {
        List<Move> m = new ArrayList<>();
        m.add(new Move("ORA ORA", 10, 0, 3, false));
        m.add(new Move("Time Stop", 0, 999, 1, true)); // special
        return m;
    }

    private List<Move> demoMovesForVillain() {
        List<Move> m = new ArrayList<>();
        m.add(new Move("The World", 12, 0, 3, false));
        m.add(new Move("Space Ripper", 18, 0, 1, false));
        return m;
    }
}
