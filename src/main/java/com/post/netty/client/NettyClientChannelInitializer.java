package com.post.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 客户端初始化，客户端与服务器端连接一旦创建，这个类中方法就会被回调，设置出站编码器和入站解码器，客户端服务端编解码要一致
 **/
@Component
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(SocketChannel channel) {

        channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast(nettyClientHandler);
    }
}