package fliplus.spyglassastronomysync.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class RequestDataPacket implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, RequestDataPacket> STREAM_CODEC = CustomPacketPayload.codec((packet, buffer) -> {}, buffer -> new RequestDataPacket());
    public static final ResourceLocation REQUEST_DATA_PACKET = ResourceLocation.fromNamespaceAndPath(SpyglassAstronomySync.MOD_ID, "request_data_packet");
    public static final CustomPacketPayload.Type<RequestDataPacket> TYPE = new CustomPacketPayload.Type<>(REQUEST_DATA_PACKET);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
