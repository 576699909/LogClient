package com.ding.log.client.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ding.log.client.message.Message;
import com.ding.log.client.message.MessageFactory;
import com.ding.log.client.message.MessageSender;
import com.ding.log.client.message.bean.GroupBean;
import com.ding.log.client.message.impl.MysqlMessageDefault;
import com.ding.log.client.util.DirectorySizeUtil;
import com.ding.log.client.util.HttpUtil;
import com.ding.log.client.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ding.log.client.util.Constants.URL_GET_GROUP_LIST;
import static com.ding.log.client.util.Constants.URL_HEARTBEAT_MONITOR;

@Slf4j
@Component
public class ReportTimer {
    @Autowired
    private MessageSender messageSender;
    @Value("${log.home.url:http://127.0.0.1:80}")
    private String URL;
    @Value("${client.app.name:client1}")
    private String appName;

    /**
     * 定时任务
     */
    @Scheduled(cron = "${report.timer.cron}")
    public void sacnData() {
        long startTime=System.currentTimeMillis();
        log.info("begin.sacnData:{}", startTime);
        Map<String,String> map = new HashMap();
        map.put("clientAppName",appName);

        String homeUrl=URL + URL_GET_GROUP_LIST;
        log.info("result.homeUrl:{}", homeUrl);
        //从分组组件获取
        String result = HttpUtil.getRestTemplate().getForObject(homeUrl, String.class,map);
        log.info("result.sacnData:{}", result);
        List<GroupBean>list= JSON.parseObject(result, new TypeReference<ArrayList<GroupBean>>() {});
        for (GroupBean bean: list) {
            sendMessage(bean);
        }
        long endTime=System.currentTimeMillis();
        log.info("end.sacnData:{}", (float)(endTime-startTime)/1000);

    }

    /**
     * 单条发送
     * @param bean
     */
    private void sendMessage(GroupBean bean){
        Long size = DirectorySizeUtil.getDirectorySize(bean.getDirectory());
        if (size == null) {
            //获取数据异常
           return;
        }
        //ID可以优化
        Message message= MessageFactory.newMysqlMessage(bean.getGroupName(),bean.getSystemId());
        //设置公共值
        SystemUtil.setSystemProperty(message);
        //主要监控的目录文件大小
        message.addData("systemId.disk.total",size+"");
        messageSender.send(message);
        log.info("信息已放入队列:{}", message);
    }



}
