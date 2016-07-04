package org.yannis.ringtail.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.yannis.ringtail.rpc.util.SerializationUtils;

import java.util.List;

/**
 * Created by dell on 2016/7/4.
 */
public class NettyDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public NettyDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = SerializationUtils.deserialize(data, genericClass);
        out.add(obj);
    }
}
