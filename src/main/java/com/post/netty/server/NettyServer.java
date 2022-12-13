package com.post.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 * netty服务启动类
 */

@Slf4j
@Component
public class NettyServer {
    @Autowired
    NettyServerChannelInitializer nettyServerChannelInitializer;
    @Value("${server.port}")
    private int port;

    @Async
    public void startUp() throws Exception {
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    // 绑定线程池
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    //编码解码
                    .childHandler(nettyServerChannelInitializer)
                    //服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保持长连接，2小时无数据激活心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("netty服务器开始监听端口：" + port);
            //关闭channel和块，直到它被关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}