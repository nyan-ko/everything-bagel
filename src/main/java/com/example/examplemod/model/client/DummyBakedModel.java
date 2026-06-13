package com.example.examplemod.model.client;

import com.example.examplemod.ModComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DummyBakedModel implements IDynamicBakedModel {
    private static final Material MISSING_TEXTURE =
            new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation());
    public Map<String, BakedModel> modelsByVariation;
    public ItemOverrides overrides;

    public DummyBakedModel(Map<String, BakedModel> variations) {
        this.modelsByVariation = variations;
        this.overrides = new ItemModelOverride();
    }

    // Unused !
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return MISSING_TEXTURE.sprite();
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    public BakedModel getOrDefault(String variation, BakedModel model) {
        return modelsByVariation.getOrDefault(variation, model);
    }

    public static class ItemModelOverride extends ItemOverrides {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
            var variation = stack.get(ModComponents.INGREDIENT);
            if (model instanceof DummyBakedModel) {
                return ((DummyBakedModel) model).getOrDefault(variation, model);
            }
            return model;
        }
    }

    public static class FullBakedModel implements IDynamicBakedModel {
        public List<BakedQuad> baseQuads;

        public FullBakedModel(List<BakedQuad> baseQuads) {
            this.baseQuads = baseQuads;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
            return baseQuads;
        }

        @Override
        public boolean useAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean usesBlockLight() {
            return false;
        }

        @Override
        public boolean isCustomRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return MISSING_TEXTURE.sprite();
        }

        @Override
        public ItemOverrides getOverrides() {
            return ItemOverrides.EMPTY;
        }
    }
}
