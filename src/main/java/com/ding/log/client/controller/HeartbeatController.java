package com.ding.log.client.controller;

import com.ding.log.client.message.Message;
import com.ding.log.client.message.MessageFactory;
import com.ding.log.client.message.MessageSender;
import com.ding.log.client.message.impl.MysqlMessageDefault;
import com.ding.log.client.message.io.NettyClient;
import com.ding.log.client.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.ding.log.client.util.Constants.URL_HEARTBEAT_MONITOR;

@Slf4j
@RestController
@RequestMapping(value = "/heartbeat")
public class HeartbeatController {
    @Value("${log.server.url}")
    private String URL;
    @Autowired
    private MessageSender messageSender;
    @ResponseBody
    @RequestMapping(value = "/monitor")
    public String monitor() {
        return "success";
    }


    @ResponseBody
    @RequestMapping(value = "/shutdown")
    public String shutdown() {
        log.info("关闭消息发送");
        messageSender.shutdown();
        return "success";
    }
}
