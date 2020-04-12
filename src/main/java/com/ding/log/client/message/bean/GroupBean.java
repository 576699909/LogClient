package com.ding.log.client.message.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Daniel
 */
@Data
@ToString
public class GroupBean implements Serializable {
    /**
     * 组名
     */
    private String groupName;
    /**
     * 要扫描的机器路径
     */
    private String directory;
    /**
     * 被监控机器目录的ID
     */
    private String systemId;
}
