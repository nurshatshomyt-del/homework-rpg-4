package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            throw new IllegalArgumentException("Teams and skills must not be null");
        }

        RaidResult result = new RaidResult();
        int rounds = 0;

        while (teamA.isAlive() && teamB.isAlive()) {
            rounds++;
            result.setRounds(rounds);

            teamASkill.cast(teamB);
            result.addLine("Round " + rounds + ": Team A used " + teamASkill.getSkillName() + " on Team B");

            if (!teamB.isAlive()) {
                result.setWinner("Team A");
                result.addLine("Team B defeated.");
                break;
            }

            teamBSkill.cast(teamA);
            result.addLine("Round " + rounds + ": Team B used " + teamBSkill.getSkillName() + " on Team A");

            if (!teamA.isAlive()) {
                result.setWinner("Team B");
                result.addLine("Team A defeated.");
                break;
            }

        }
        if (teamA.isAlive() && teamB.isAlive()) {
            result.setWinner("Draw");
            result.addLine("Max rounds reached or both teams still alive.");
        }

        return result;
    }
}
