package com.ding.log.client.message;

/**
 * @author Daniel
 * 消息发送接口
 */
public interface MessageSender {
    /**
     * 发送消息
     * @param message
     */
    public void send(Message message);

    /**
     * 关闭while ture
     */
    public void shutdown();
}
