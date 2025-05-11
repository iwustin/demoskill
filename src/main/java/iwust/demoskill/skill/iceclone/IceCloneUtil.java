package iwust.demoskill.skill.iceclone;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import iwust.demoskill.util.MathUtil;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.UUID;

public final class IceCloneUtil {
    public static void spawnParticles(@Nonnull final Location center,
                                      final boolean forCopy,
                                      final double radius) {
        int count = 100;
        if(forCopy) {
            count = 30;
        }
        for(int i = 0;i < count;i++) {
            Vector randomOffset = MathUtil.getRandomOffset(radius);

            Location spawnLocation = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
            spawnLocation.add(randomOffset);

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(133, 198, 255), 1.0f);
            if (((int) randomOffset.getX()) % 2 == 0) {
                dustOptions = new Particle.DustOptions(Color.WHITE, 1.0f);
            }

            spawnLocation.getWorld().spawnParticle(
                    Particle.REDSTONE,
                    spawnLocation,
                    1,
                    dustOptions
            );

            spawnLocation.getWorld().spawnParticle(
                    Particle.SNOWFLAKE,
                    spawnLocation,
                    1,
                    0,0,0,
                    0.1
            );
        }
    }

    public static int createNMSPlayerCopy(@Nonnull final Player targetPlayer) {
        CraftPlayer craftPlayer = (CraftPlayer) targetPlayer;

        MinecraftServer server = craftPlayer.getHandle().getServer();
        if (server == null) {
            return -1;
        }

        ServerLevel level = craftPlayer.getHandle().getLevel();

        UUID uuid = UUID.randomUUID();

        ServerPlayer serverPlayer = new ServerPlayer(server, level, new GameProfile(uuid, ""));

        serverPlayer.setPos(
                targetPlayer.getLocation().getX(),
                targetPlayer.getLocation().getY(),
                targetPlayer.getLocation().getZ()
        );

        serverPlayer.getGameProfile().getProperties().put(
                "textures",
                new Property("textures",
                        "ewogICJ0aW1lc3RhbXAiIDogMTY0NDUwODY5MzkwMiwKICAicHJvZmlsZUlkIiA6ICI5ZDIyZGRhOTVmZGI0MjFmOGZhNjAzNTI1YThkZmE4ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTYWZlRHJpZnQ0OCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mZjgzZjI1NWMzNTYwNWJmMmViN2QzMTRkMWYxNTBjMTZmODZhZDBjMzEzMzQ5ZmQxYzA4ZDRkZGJiZmU1OTU3IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=",
                        "lcJTJL4/F8Jat4eUdDElw5XOkrURZ3fH/3Mc7f+KbColZgd1ysLN+0iXBgiFavpfO63gagbm69PDPCXDrh9BM8BEzw32c6GAdTZR/M3kFC1CyRkx1VnXQ/KYeZTaaeSXOXBRk785XRRLW05SvT5wT5e0Ks6o5B9CsAbXGeIDMPR5vtPA66W8zIRyftDsYFq9sfuNIwjXOBaSa1UzoxC9wT3RhXc1ypCohElZbc7R96yqgJrB45L8yuk5BQPP5k/SaolL5AFVYKgb0VXfjf6+O/cRuz45Dfs1H5DtHdrSNQq9hxrsxzVqPdr+sNQU0xNojZFDX4Q41bc+9MjbKHZ6MTe08zaJiPFWmrTi2qPBXppiAyxaLvF0QJSN4K+mj9b3TEi7bMOPsW2/Ef0aEUY2kVRD9lqlw2UYzQFi3B2HNzSn9FMFhb0DpVxeF1rleWwxBEs4kiIsZFmBHdNULZc87U/ne7okB1L5yvCO4r4jJ9av8XskP3hpU4BFOMGEW+iplwsUMfSOLVB2OmjBBqn9hii3jsoH2NvFSZifiyimPbLe2WATwjePx5+djw+N0r2lSVWdXZWSPhDsxEoed3gxC7Jq+ZUepacfKlGJcszF4uzUDvMa1Gr4tdYEYKwmDHDTeCoS5i/pTO43HVnS1KV16jWK9aJ6qY5Qi5R3Wh1k1Mk="
                )
        );

        for (Player player : Bukkit.getOnlinePlayers()) {
            ServerGamePacketListenerImpl ps = ((CraftPlayer) player).getHandle().connection;
            ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, serverPlayer));
            ps.send(new ClientboundAddPlayerPacket(serverPlayer));
        }

        return serverPlayer.getId();
    }

    public static void removeNMSPlayerCopy(final int id) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ServerGamePacketListenerImpl ps = ((CraftPlayer) player).getHandle().connection;
            ps.send(new ClientboundRemoveEntitiesPacket(id));
        }
    }
}

