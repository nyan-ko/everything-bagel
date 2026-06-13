package com.example.examplemod.model.client;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.neoforged.neoforge.client.model.geometry.UnbakedGeometryHelper;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;

import java.util.*;
import java.util.function.Function;

public class DynamicGeometry implements IUnbakedGeometry<DynamicGeometry> {
    private final Map<String, Material> variations;

    public DynamicGeometry(Map<String, String> variations) {
        this.variations = new HashMap<>();
        for (Map.Entry<String, String> entry : variations.entrySet()) {
            Material material = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse(entry.getValue()));
            System.out.println(entry.getKey() + " " + entry.getValue());
            this.variations.put(entry.getKey(), material);
        }
    }

    public List<BakedQuad> buildItemQuads(TextureAtlasSprite sprite) {
        List<BakedQuad> quads = new ArrayList<>();
        quads.add(buildQuad(sprite, Direction.SOUTH));
        quads.add(buildQuad(sprite, Direction.NORTH));
        return quads;
    }

    public BakedQuad buildQuad(TextureAtlasSprite sprite, Direction facing) {
        QuadBakingVertexConsumer consumer = new QuadBakingVertexConsumer();

        consumer.setSprite(sprite);
        consumer.setDirection(facing);
        consumer.setTintIndex(0);
        consumer.setShade(false);
        consumer.setHasAmbientOcclusion(false);

        float z = 0.5f;
        int r = 0xFF, g = 0xFF, b = 0xFF, a = 0xFF;

        float u0 = sprite.getU(0f);
        float u1 = sprite.getU(1f);
        float v0 = sprite.getV(0f);
        float v1 = sprite.getV(1f);

        float nx = facing.getStepX();
        float ny = facing.getStepY();
        float nz = facing.getStepZ();

        if (facing == Direction.SOUTH) {
            consumer.addVertex(0, 0, z).setColor(r, g, b, a).setUv(u0, v1).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(1, 0, z).setColor(r, g, b, a).setUv(u1, v1).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(1, 1, z).setColor(r, g, b, a).setUv(u1, v0).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(0, 1, z).setColor(r, g, b, a).setUv(u0, v0).setUv2(0, 0).setNormal(nx, ny, nz);
        } else {
            consumer.addVertex(1, 0, z).setColor(r, g, b, a).setUv(u1, v1).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(0, 0, z).setColor(r, g, b, a).setUv(u0, v1).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(0, 1, z).setColor(r, g, b, a).setUv(u0, v0).setUv2(0, 0).setNormal(nx, ny, nz);
            consumer.addVertex(1, 1, z).setColor(r, g, b, a).setUv(u1, v0).setUv2(0, 0).setNormal(nx, ny, nz);
        }

        return consumer.bakeQuad();
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        Map<String, BakedModel> modelsByVariation = new HashMap<>();
        for (Map.Entry<String, Material> entry : variations.entrySet()) {
            var sprite = spriteGetter.apply(entry.getValue());
            var quads = buildItemQuads(sprite);
            var model = new DummyBakedModel.FullBakedModel(quads);
            modelsByVariation.put(entry.getKey(), model);
        }
        return new DummyBakedModel(modelsByVariation);
    }
}
