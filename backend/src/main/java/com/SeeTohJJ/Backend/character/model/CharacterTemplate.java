package com.SeeTohJJ.Backend.character.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "character_templates")
public class CharacterTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String templateId;

    private String name;
    private String description;

    private Integer startingCash;
    private Integer startingHealth;
    private Integer startingFocus;
    private Integer startingHappiness;
    private Integer startingStress;

    private Integer startingDay;

    private String difficultyModifier;

    @OneToMany(mappedBy = "template")
    private List<Character> characters;



}