package fliplus.spyglassastronomysync.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class HandShakePacket implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, HandShakePacket> STREAM_CODEC = CustomPacketPayload.codec((packet, buffer) -> {}, buffer -> new HandShakePacket());
    public static final ResourceLocation HAND_SHAKE_PACKET = ResourceLocation.fromNamespaceAndPath(SpyglassAstronomySync.MOD_ID, "hand_shake_packet");
    public static final CustomPacketPayload.Type<HandShakePacket> TYPE = new CustomPacketPayload.Type<>(HAND_SHAKE_PACKET);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
