package com.post.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 **/

@Slf4j
@Component
public class NettyClient {
    @Autowired
    private NettyClientChannelInitializer nettyClientChannelInitializer;
    @Value("${client.host}")
    private String host;
    @Value("${client.port}")
    private int port;
    @Value("${client.size}")
    private int size;

    @Async
    public void startUp() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(nettyClientChannelInitializer);

            ChannelFuture future = bootstrap.connect(host, port).sync();
            log.info("已经连接到:" + host + ":" + port);
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully(); // 关闭线程池和释放所有资源
        }
    }

}