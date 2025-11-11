package com.rajdeep.dao;

import com.rajdeep.model.Character;
import java.util.List;

public interface CharacterDAO {
    void addCharacter(Character character) throws Exception;
    Character getCharacterById(int id) throws Exception;
    List<Character> getAllCharacters() throws Exception;
    void updateCharacter(Character character) throws Exception;
    void deleteCharacter(int id) throws Exception;
}

