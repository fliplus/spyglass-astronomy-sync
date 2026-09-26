package dev.fliplus.spyglassastronomysync.network;

import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record DataPayload(String data) implements CustomPacketPayload {
    public static final Identifier DATA_PAYLOAD_ID = SpyglassAstronomySync.id("data");

    public static final CustomPacketPayload.Type<DataPayload> TYPE = new CustomPacketPayload.Type<>(DATA_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, DataPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        DataPayload::data,
        DataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
