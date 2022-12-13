package com.post.netty.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(ctx.channel().id() + ":ClientHandler Active");
        ctx.writeAndFlush("Netty rocks!");
    }

    /**
     * @author xiongchuan on 2019/4/28 16:10
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.close();
        log.info("服务端终止了服务");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("服务端发生异常【" + cause.getMessage() + "】");
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        // 记录接收到的消息
        log.info("Client received: " + msg);
//        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(string, CharsetUtil.UTF_8));
    }
}
