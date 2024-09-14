package org.e11eman.crackutilities.utilities.rendering;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.e11eman.crackutilities.wrappers.Player;

import java.time.LocalDate;
import java.time.LocalTime;

public class ItemScreen extends Screen {
    public ItemScreen(int width, int height, String character) {
        super(width, height, character);
    }

    @Override
    public void draw() {
        ItemStack item = Player.getPlayer().getInventory().getMainHandStack();
        LoreComponent loreComponent = LoreComponent.DEFAULT;

        for (int y = 0; y < height; y++) {
            MutableText line = MutableText.of(PlainTextContent.of(""));
            for (int x = 0; x < width; x++) {
                line.append(this.character).setStyle(Style.EMPTY.withColor(Integer.parseInt(screen[x][y].toUpperCase(), 16)).withItalic(false).withBold(false));
            }
            loreComponent = loreComponent.with(Text.of(line));
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        MutableText name = MutableText.of(PlainTextContent.of(String.format("Created %s/%s/%s at %s:%s:%s",
                day,
                month,
                year,
                hour,
                minute,
                second
        ))).setStyle(Style.EMPTY.withColor(0x808080).withItalic(false).withBold(false));

        item.set(DataComponentTypes.CUSTOM_NAME, name);
        item.set(DataComponentTypes.LORE, loreComponent);

        Player.setMainHand(item);
    }
}
