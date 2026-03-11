package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;
import java.util.List;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) {
            return;
        }
        List<CombatNode> children = target.getChildren();
        if (children.isEmpty()) {
            target.takeDamage(resolvedDamage());
        } else {
            for (CombatNode child : children) {
                cast(child);
            }
        }
    }
}
