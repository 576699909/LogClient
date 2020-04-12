package com.ding.log.client.message.impl;

import com.ding.log.client.message.Message;
import com.ding.log.client.message.MessageSender;
import com.ding.log.client.message.io.NettyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Netty实现的信息发送
 */
@Slf4j
@Service
public class NettyMessageSender implements MessageSender {
    private static EventLoopGroup group = new NioEventLoopGroup();
    private static Channel channel;
    /**
     * 发消息的队列
     */
    private static LinkedBlockingQueue<Message> queue;
    /**
     * 是否继续while true
     */
    private static transient boolean active = false;
    /**
     * 发送数据线程
     */
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            active = true;
            while (active) {
                try {
                    Message message = queue.poll();
                    if (message != null && message.getData() != null) {
                        channel.writeAndFlush(message.getData());
                        log.info("数据已发送:{}", message);
                    } else {
                        Thread.sleep(50);
                    }
                } catch (Throwable t) {
                    log.error("send message error netty ", t);
                }
            }
        }
    };

    /**
     * 初始化
     */
    private static void initialize() {
        /**
         * 第一次初始化
         */
        if (queue == null) {
            queue = new LinkedBlockingQueue<Message>(500);
            try {
                //初始化参数
                Bootstrap bootstrap = new Bootstrap()
                        .group(group)
                        //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                        .option(ChannelOption.TCP_NODELAY, true)
                        .channel(NioSocketChannel.class)
                        .handler(new NettyClientInitializer());
                ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();
                channel = future.channel();
                //初始化发送的whileTrue
                whileSend();
            } catch (InterruptedException e) {
                log.error("LogClient initialize Error", e);
            }
        }
    }

    /**
     * 轮训发送信息
     */
    private static void whileSend() throws InterruptedException {
        new Thread(runnable).start();
    }

    @Override
    public void send(Message message) {
        initialize();
        log.info("发送信息:{}",message);
        //发送消息
        if (!queue.offer(message)) {
            log.error("队列已满");
        }
    }

    @Override
    public void shutdown() {
        try {
            active = false;
            // 等待连接被关闭
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("LogClient shutdown Error", e);
        } finally {
            group.shutdownGracefully();
        }

    }
}
