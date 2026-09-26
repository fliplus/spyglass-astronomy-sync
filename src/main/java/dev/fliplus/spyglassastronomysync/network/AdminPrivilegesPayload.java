package dev.fliplus.spyglassastronomysync.network;

import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record AdminPrivilegesPayload(boolean allowAdminPrivileges) implements CustomPacketPayload {
    public static final Identifier ADMIN_PRIVILEGES_PAYLOAD_ID = SpyglassAstronomySync.id("admin_privileges");

    public static final CustomPacketPayload.Type<AdminPrivilegesPayload> TYPE = new CustomPacketPayload.Type<>(ADMIN_PRIVILEGES_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, AdminPrivilegesPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        AdminPrivilegesPayload::allowAdminPrivileges,
        AdminPrivilegesPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
