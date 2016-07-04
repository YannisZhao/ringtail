package org.yannis.ringtail.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.yannis.ringtail.rpc.util.SerializationUtils;

/**
 * Created by dell on 2016/7/4.
 */
public class NettyEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public NettyEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtils.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
