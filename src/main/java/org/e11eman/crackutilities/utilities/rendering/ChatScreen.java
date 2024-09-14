package org.e11eman.crackutilities.utilities.rendering; //dude did you really leave out this semicolon in ur code?

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.e11eman.crackutilities.utilities.CClient;

public class ChatScreen extends Screen{
    public ChatScreen(int width, int height, String character) {
        super(width, height, character);
    }
    public String selector = "@a";

    @Override
    public void draw() {
        JsonArray tellraw = new JsonArray();

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JsonObject pixel = new JsonObject();

                pixel.add("text", new JsonPrimitive(this.character));
                pixel.add("color", new JsonPrimitive(screen[x][y]));

                tellraw.add(pixel);
            }

            JsonObject newline = new JsonObject();

            newline.add("text", new JsonPrimitive("\n"));

            tellraw.add(newline);
        }

        CClient.commandCoreSystem.run("minecraft:tellraw " + selector + " " + tellraw);
    }
}