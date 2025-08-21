package fliplus.spyglassastronomysync.network;

import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DataPacket(String data, int revision) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, DataPacket> STREAM_CODEC = CustomPacketPayload.codec(DataPacket::write, DataPacket::new);
    public static final ResourceLocation ADD_CONSTELLATION_PACKET = ResourceLocation.fromNamespaceAndPath(SpyglassAstronomySync.MOD_ID, "add_constellation_packet");
    public static final CustomPacketPayload.Type<DataPacket> TYPE = new CustomPacketPayload.Type<>(ADD_CONSTELLATION_PACKET);

    public DataPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readInt());
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(data);
        buffer.writeInt(revision);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
