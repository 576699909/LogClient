package com.ding.log.client.message;

/**
 *  @author Daniel
 *  @see com.ding.log.client.message.impl.MysqlMessageDefault
 */
public interface Message {
    /**
     * 未发送
     */
    public static final String INIT = "-1";
    /**
     * 发送失败
     */
    public static final String ERROR = "0";
    /**
     * 发送成功
     */
    public static final String SUCCESS = "1";
    /**
     * 添加监控键值对
     * @param key
     * @param value
     */
    public void addData(String key, String value);


    /**
     * 返回之前的键值对&name=mysql&age=18
     * @return
     */
    public CharSequence getData();

    /**
     * 返回使用的消息名 本次都是mysql磁盘监控
     * @return
     */
    public String getName();


    /**
     * 是否发送完成 不管对错
     * @return
     */
    public boolean isCompleted();

    /**是否发送成功
     * @return
     */
    public boolean isSuccess();

    /**
     * 设置发送状态 默认是-1 未发送
     * @param status
     */
    public void setStatus(String status);

}
