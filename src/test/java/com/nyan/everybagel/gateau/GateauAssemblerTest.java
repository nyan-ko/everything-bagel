package com.nyan.everybagel.gateau;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GateauAssemblerTest {
    private Map<GateauSet, GateauSet> mixes;

    @BeforeEach
    public void setup() {
        mixes = new HashMap<>();
        mixes.put(GateauSet.of(GateauDefaults.IRON, GateauDefaults.COPPER), GateauSet.of(GateauDefaults.AMETHYST));
        mixes.put(GateauSet.of(GateauDefaults.IRON), GateauSet.of(GateauDefaults.STONE));
        mixes.put(GateauSet.of(GateauDefaults.IRON, GateauDefaults.DIAMOND), GateauSet.of(GateauDefaults.WOOD));
    }

    @Test
    public void testSimple() {
        GateauSet inputs = GateauSet.of(GateauDefaults.IRON, GateauDefaults.COPPER);

        var results = GateauAssembler.computeOutput(inputs, mixes);

        assert(results.size() == 2);
    }
}
