package com.example.addon.modules.lodestonebullshit;

import meteordevelopment.meteorclient.systems.modules.Module;

import com.example.addon.AddonTemplate;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

public class LodestoneBullshit extends Module {
    public LodestoneBullshit() {
        super(AddonTemplate.CATEGORY, "Lodestone-Bullshit", "Reads coords from a lodestone compass.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) return;

        ItemStack stack = mc.player.getMainHandStack();

        // Check if it's a compass
        if (stack.getItem() != Items.COMPASS) return;

        // Get the lodestone tracker component
        LodestoneTrackerComponent tracker =
            stack.get(DataComponentTypes.LODESTONE_TRACKER);

        if (tracker == null) return;

        // Get the target (Optional<GlobalPos>)
        var targetOpt = tracker.target();

        if (targetOpt.isEmpty()) {
            info("No lodestone bound.");
            return;
        }

        GlobalPos globalPos = targetOpt.get();
        BlockPos pos = globalPos.pos();

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        info("Lodestone at: " + x + ", " + y + ", " + z +
             " (Dim: " + globalPos.dimension().getValue() + ")");
    }
}