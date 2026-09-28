package dev.fliplus.spyglassastronomysync.network;

import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class RequestDataPayload implements CustomPacketPayload {
    public static final Identifier REQUEST_DATA_PAYLOAD_ID = SpyglassAstronomySync.id("request_data");

    public static final CustomPacketPayload.Type<RequestDataPayload> TYPE = new CustomPacketPayload.Type<>(REQUEST_DATA_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestDataPayload> STREAM_CODEC = StreamCodec.of(
        (_, _) -> {},
        _ -> new RequestDataPayload()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
