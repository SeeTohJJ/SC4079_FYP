package com.SeeTohJJ.Backend.study.constant;

public class EnergyConstant {

    public static final int ENERGY_COST_LESSON = 3;
    public static final int ENERGY_COST_QUIZ = 5;



    public static final String GET_USER_ENERGY_BY_USER_ID = """
            SELECT *
            FROM user_energy
            WHERE user_id = ?;
            """;

    public static final String UPDATE_USER_ENERGY = """
            UPDATE user_energy
            SET current_energy = ?,  last_updated = ?
            WHERE user_id = ?;
            """;

    public static final String INSERT_ENERGY = """
            INSERT INTO user_energy (user_id)
            VALUES (?);
            """;
}
