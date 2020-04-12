package com.ding.log.client.message;

import com.ding.log.client.message.impl.MysqlMessageDefault;

/**
 * 消息工厂
 */
public class MessageFactory {
    /**
     *获得公共消息体
     * @param group 组别
     * @param systemId 搜索文件夹的系统ID
     * @return
     */
    public static MysqlMessageDefault newMysqlMessage(String group, String systemId) {
        MysqlMessageDefault message=new MysqlMessageDefault();
        message.addData("group",group);
        //ID可以优化
        message.addData("systemId",systemId);
        return message;
    }}
