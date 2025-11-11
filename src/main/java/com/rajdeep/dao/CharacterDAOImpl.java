package com.rajdeep.dao;

import com.rajdeep.model.Character;
import com.rajdeep.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 Simple CharacterDAO implementation using SQLite.
 Assumes a 'characters' table with columns:
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name TEXT, level INTEGER, max_health INTEGER, power INTEGER, defense INTEGER, speed INTEGER
*/
public class CharacterDAOImpl implements CharacterDAO {

    @Override
    public void addCharacter(Character character) throws Exception {
        String sql = "INSERT INTO characters(name, level, max_health, power, defense, speed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, character.getName());
            ps.setInt(2, character.getLevel());
            ps.setInt(3, character.getMaxHealth());
            ps.setInt(4, character.getPower());
            ps.setInt(5, character.getDefense());
            ps.setInt(6, character.getSpeed());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    character.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Character getCharacterById(int id) throws Exception {
        String sql = "SELECT id, name, level, max_health, power, defense, speed FROM characters WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Character c = mapRow(rs);
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Character> getAllCharacters() throws Exception {
        String sql = "SELECT id, name, level, max_health, power, defense, speed FROM characters";
        List<Character> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public void updateCharacter(Character character) throws Exception {
        String sql = "UPDATE characters SET name=?, level=?, max_health=?, power=?, defense=?, speed=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, character.getName());
            ps.setInt(2, character.getLevel());
            ps.setInt(3, character.getMaxHealth());
            ps.setInt(4, character.getPower());
            ps.setInt(5, character.getDefense());
            ps.setInt(6, character.getSpeed());
            ps.setInt(7, character.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteCharacter(int id) throws Exception {
        String sql = "DELETE FROM characters WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Character mapRow(ResultSet rs) throws SQLException {
        Character c = new Character();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setLevel(rs.getInt("level"));
        c.setMaxHealth(rs.getInt("max_health"));
        c.setCurrentHealth(rs.getInt("max_health"));
        c.setPower(rs.getInt("power"));
        c.setDefense(rs.getInt("defense"));
        c.setSpeed(rs.getInt("speed"));
        return c;
    }
}
