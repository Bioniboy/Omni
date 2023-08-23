package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.clarity.AstralProject;
import com.bion.omni.omnimod.powers.clarity.BlindnessImmunity;
import com.bion.omni.omnimod.powers.clarity.BlockHistory;
import net.minecraft.util.Formatting;

public class Clarity extends Element {
    @Override
    public Formatting getColor() {
        return Formatting.BLUE;
    }

    @Override
    public String getName() {
        return "Clarity";
    }

    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "astralProject":
                yield new AstralProject();
            case "blockHistory":
                yield new BlockHistory(level);
            case "blindnessImmunity":
                yield new BlindnessImmunity();
            default:
                yield null;
        };
    }

    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }

    @Override
    public String[] getPowerIds() {
        return new String[] {
                "astralProject",
                "blockHistory",
                "blindnessImmunity"
        };
    }
}
