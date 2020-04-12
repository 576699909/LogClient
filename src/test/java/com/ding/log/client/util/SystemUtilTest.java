package com.ding.log.client.util;

import com.ding.log.client.message.Message;
import com.ding.log.client.message.MessageFactory;
import org.junit.Assert;
import org.junit.Test;


public class SystemUtilTest {

    @Test
    public void setSystemProperty() {
        Message message = MessageFactory.newMysqlMessage("2", "");
        SystemUtil.setSystemProperty(message);
        Assert.assertNotNull(message);
    }
}
