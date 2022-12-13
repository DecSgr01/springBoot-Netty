package com.post.netty;

import com.post.netty.client.NettyClient;
import com.post.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author xyp
 * @date 2022/12/6 14:19
 */
@Component
@Slf4j
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    NettyClient nettyClient;
    @Autowired
    NettyServer nettyServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                nettyClient.startUp();
                nettyServer.startUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
