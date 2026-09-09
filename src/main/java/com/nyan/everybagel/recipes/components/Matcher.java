package com.nyan.everybagel.recipes.components;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record Matcher(Type type, Ingredient match) {
    public static final MapCodec<Matcher> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            StringRepresentable.fromEnum(Type::values).fieldOf("match_type").forGetter(Matcher::type),
            Ingredient.CODEC.optionalFieldOf("match", Ingredient.EMPTY).forGetter(Matcher::match)
    ).apply(instance, Matcher::new));

    public static final StreamCodec<ByteBuf, Matcher> STREAM_CODEC = ByteBufCodecs.fromCodec(MAP_CODEC.codec());

    public static final Matcher ALL = new Matcher(Type.ALL, Ingredient.EMPTY);
    public static final Matcher NONE = new Matcher(Type.NONE, Ingredient.EMPTY);

    private Matcher(Type type) {
        this(type, Ingredient.EMPTY);
    }

    private Matcher(Ingredient match) {
        this(Type.SPECIFIC, match);
    }

    public static Matcher of(Ingredient match) {
        return new Matcher(match);
    }

    public boolean test(ItemStack input) {
        if (type == Type.ALL) {
            return true;
        }
        else if (type == Type.NONE) {
            return false;
        }
        else {
            return match.test(input);
        }
    }

    public boolean none() {
        return type == Type.NONE;
    }

    public enum Type implements StringRepresentable {
        ALL("all"),
        NONE("none"),
        SPECIFIC("specific"),;

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
