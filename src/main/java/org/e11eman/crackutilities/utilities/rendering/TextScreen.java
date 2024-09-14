package org.e11eman.crackutilities.utilities.rendering;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.SecureRandomStuff;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;

public class TextScreen extends Screen {
    public double x;
    public double y;
    public double z;
    public String tag = "CUtilitiesScreenShare";
    public float separation = 0.125f;

    public TextScreen(int width, int height, double x, double y, double z, String character) {
        super(width, height, character);

        this.x = x;
        this.y = y;
        this.z = z;

        System.out.println("character: " + character);
    }

    @Override
    public void draw() {
        ArrayList<JsonArray> names = new ArrayList<>();
        for(int y =0; y < height; y++) {
            JsonArray name = new JsonArray();
            for(int x = 0; x < width; x++) {
                JsonObject pixel = new JsonObject();

                pixel.add("text", new JsonPrimitive(this.character));
                pixel.add("color", new JsonPrimitive(screen[x][y]));

                name.add(pixel);
            }
            names.add(name);
        }

        for (int i = 0; i < names.size(); i++) {
            CClient.commandCoreSystem.run("execute as @e[tag=" + this.tag + i + "] run data merge entity @s {text:'" + names.get(i).getAsJsonArray() + "'}");
        }
    }

    public void update() {
        ArrayList<JsonArray> names = new ArrayList<>();

        for(int y =0; y < height; y++) {
            JsonArray name = new JsonArray();

            for(int x = 0; x < width; x++) {
                JsonObject pixel = new JsonObject();

                pixel.add("text", new JsonPrimitive(this.character));
                pixel.add("color", new JsonPrimitive(screen[x][y]));

                name.add(pixel);
            }
            names.add(name);
        }

        for(int i = 0; i < this.height; i++) {
            int initialheight = ((int) (this.y + ((separation * this.height) / 2)));
            CClient.commandCoreSystem.run("kill @e[tag=" + this.tag + i + "]");
            CClient.commandCoreSystem.run(("summon text_display " + x + " " + (initialheight - (i * separation)) + " " + y + " {" +
                    "Tags:[\"" + this.tag + i + "\"]," +
                    "text:'{\"text\":\"" + names.get(i) + "\"}'," +
                    "see_through: 1b," +
                    "line_width: 64000, " +
                    "background: true, " +
                    "text_opacity:-1, " +
                    "transformation:{" +
                        "left_rotation:[0f,0f,0f,1f]," +
                        "right_rotation:[0f,0f,0f,1f]," +
                        "translation:[0f,0f,0f]," +
                        "scale:[0.5f,0.5f,0.5f]}" +
                    "}"));

            CClient.commandCoreSystem.run(
                    //Removed for preservation of exploit ;)
                    //No u
                    ""
                    //TODO: figure out what this originally was, if we even need it
            );
        }
    }

    public void cleanup() {
        for(int i = 0; i < this.height; i++) {
            CClient.commandCoreSystem.run("kill @e[tag=" + this.tag + i + "]");
        }
    }
}