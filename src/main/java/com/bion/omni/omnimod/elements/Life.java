package com.bion.omni.omnimod.elements;

import com.bion.omni.omnimod.powers.Power;
import com.bion.omni.omnimod.powers.air.*;
import com.bion.omni.omnimod.powers.life.*;
import net.minecraft.util.Formatting;


public class Life extends Element {
    @Override
    public Power getPower(String id, Integer level) {
        return switch (id) {
            case "photosynthesis":
                yield new Photosynthesis();
            case "transformOcelot":
                yield new TransformOcelot();
            case "transformChicken":
                yield new TransformChicken();
            case "transformSheep":
                yield new TransformSheep();
            case "transformCow":
                yield new TransformCow();
            case "transformPig":
                yield new TransformPig();
            case "transformFish":
                yield new TransformFish();
            default:
                yield null;
        };
    }
    @Override
    public String[] getPowerIds() {
        return new String[] {
                "photosynthesis",
                "transformOcelot",
                "transformChicken",
                "transformSheep",
                "transformCow",
                "transformPig",
                "transformFish"
        };
    }
    @Override
    public Power getPower(String id) {
        return getPower(id, 1);
    }
    @Override
    public String getName() {
        return "Life";
    }

    @Override
    public Formatting getColor() {
        return Formatting.DARK_GREEN;
    }


}
