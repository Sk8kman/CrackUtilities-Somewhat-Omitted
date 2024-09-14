package org.e11eman.crackutilities.wrappers;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.*;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.*;

@SuppressWarnings("unused")
public class Player {
    private static final LastSeenMessagesCollector lastSeenMessagesCollector = new LastSeenMessagesCollector(120);
    private static final CommandDispatcher<CommandSource> commandDispatcher = new CommandDispatcher<>();
    public static boolean inWorld = false;


    public static ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static ClientPlayNetworkHandler getNetworkHandler() { return getPlayer().networkHandler; }

    public static ClientPlayerInteractionManager getInteractionManager() { return  MinecraftClient.getInstance().interactionManager; }

    public static Entity getCameraInstance() { return MinecraftClient.getInstance().getCameraEntity(); }

    public static String getUsername() {
        if(getPlayer() != null) return getPlayer().getGameProfile().getName();
        return "unknown";
    }

    public static Block getBlock(BlockPos blockPos) {
        return getPlayer().clientWorld.getBlockState(blockPos).getBlock();
    }

    public static BlockState getBlockState(BlockPos blockPos) {
        return getPlayer().clientWorld.getBlockState(blockPos);
    }

    public static void placeBlock(BlockPos blockPos) {
        getInteractionManager().interactBlock(getPlayer(), Hand.MAIN_HAND, new BlockHitResult(blockPos.toCenterPos(), Direction.DOWN, blockPos, false));
    }

    public static String getUuid() {
        if(getPlayer() != null) return getPlayer().getUuidAsString();
        return "00000000-0000-0000-0000-000000000000";
    }

    public static Vec3d getPosition() {
        if(getPlayer() != null) return getPlayer().getPos();
        return new Vec3d(0,0,0);
    }

    public static void setMainHand(ItemStack item) {
        Player.getInteractionManager().clickCreativeStack(item, 36 + Player.getPlayer().getInventory().selectedSlot);
    }

    public static void setUsername(String username) {
        MinecraftClient client = MinecraftClient.getInstance();
        //TODO: implement changing session profile name
    }

    public static void sendChat(String message) {
        if(getPlayer() != null) {
            if(message.startsWith("/")) {
                getNetworkHandler().sendChatCommand(message.substring(1));
            } else {
                getNetworkHandler().sendChatMessage(message);
            }

        }

    }

    public static void alertClient(Text chat) {
        if(getPlayer() != null) getPlayer().sendMessage(chat);
    }

    public static void rightClick(Hand hand) {
        if(getInteractionManager() != null && getPlayer() != null) {
            getInteractionManager().interactItem(getPlayer(), hand);
        }
    }

    public static float getYaw() {
        if(getPlayer() != null) return getPlayer().getYaw();
        return 0;
    }

    public static float getPitch() {
        if(getPlayer() != null) return getPlayer().getPitch();
        return 0;
    }

    public static void setYaw(float yaw) {
        if(getPlayer() != null) getPlayer().setYaw(yaw);
    }

    public static void setPitch(float pitch) {
        if(getPlayer() != null) getPlayer().setPitch(pitch);
    }

    public static void setCameraYaw(float yaw) {
        if(getCameraInstance() != null) {
            getCameraInstance().setYaw(yaw);
        }
    }

    public static void setCameraPitch(float pitch) {
        if(getCameraInstance() != null) {
            getCameraInstance().setPitch(pitch);
        }
    }

    public static float getCameraYaw() {
        if(getCameraInstance() != null) {
            getCameraInstance().getYaw();
        }
        return 0;
    }

    public static float getCameraPitch() {
        if(getCameraInstance() != null) {
            getCameraInstance().getPitch();
        }
        return 0;
    }

    public static String getAddress() {
        if(getPlayer() != null) {
            return getNetworkHandler().getConnection().getAddress().toString().split("/")[1];
        }

        return "127.0.0.1";
    }

    public static boolean isOnGround() {
        if(getPlayer() != null) {
            return getPlayer().isOnGround();
        }

        return false;
    }

    public static <P extends PacketListener> void sendPacket(Packet<P> packet) {
        if(getPlayer() != null) {
            getNetworkHandler().sendPacket(packet);
        }
    }

    public static Collection<PlayerListEntry> getPlayerList() {
        if(getPlayer() != null) return getNetworkHandler().getPlayerList();
        return new ArrayList<>();
    }

    public static void connectPlayer(String addr, ServerInfo info) {
        Map<Identifier, byte[]> cookie = Map.of();
        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), MinecraftClient.getInstance(), ServerAddress.parse(addr), info, false, new CookieStorage(cookie));
    }

    public static void reconnectPlayer() {
        String lastSocket;
        ServerInfo lastInfo;
        MinecraftClient client = MinecraftClient.getInstance();
        lastSocket = Objects.requireNonNull(client.getCurrentServerEntry()).address;
        lastInfo = client.isInSingleplayer() ? null : client.getCurrentServerEntry();
        disconnectPlayer();
        connectPlayer(lastSocket, lastInfo);
    }

    public static void disconnectPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();

        boolean inSingleplayer = client.isInSingleplayer();
        boolean inRealms = client.getCurrentServerEntry() != null && client.getCurrentServerEntry().isRealm();
        assert client.world != null;
        client.world.disconnect();

        if (inSingleplayer) {
            client.disconnect(new MessageScreen(Text.literal("Disconnected\nReason: Disconnected by CrackUtilities")));
        } else {
            client.disconnect();
        }

        TitleScreen titleScreen = new TitleScreen();
        if (inSingleplayer) {
            client.setScreen(titleScreen);
        } else if (inRealms) {
            client.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            client.setScreen(new MultiplayerScreen(titleScreen));
        }
    }
}