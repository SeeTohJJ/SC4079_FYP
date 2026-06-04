package com.SeeTohJJ.Backend.character.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "character_game_state")
public class CharacterGameState {
    @Id
    private String characterId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "character_id")
    private Character character;

    private Integer dayNumber;
    private Integer semester;

    private Integer cash;
    private Integer health;
    private Integer focus;
    private Integer happiness;

    private Integer examCountdown;

    private LocalDateTime updatedAt;

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Integer getSemester() {
        return semester;
    }
}