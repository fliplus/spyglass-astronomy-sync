package fliplus.spyglassastronomysync.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record AdminPrivilegesPacket(boolean allowAdminPrivileges) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, AdminPrivilegesPacket> STREAM_CODEC = CustomPacketPayload.codec(AdminPrivilegesPacket::write, AdminPrivilegesPacket::new);
    public static final Identifier ADMIN_PRIVILEGES_PACKET = Identifier.fromNamespaceAndPath(SpyglassAstronomySync.MOD_ID, "admin_commands_packet");
    public static final CustomPacketPayload.Type<AdminPrivilegesPacket> TYPE = new CustomPacketPayload.Type<>(ADMIN_PRIVILEGES_PACKET);

    public AdminPrivilegesPacket(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(allowAdminPrivileges);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
