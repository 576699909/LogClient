package com.ding.log.client.message.impl;

import com.ding.log.client.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MysqlMessageDefault implements Message {
    private CharSequence data;
    /**
     * 是否完成
     */
    private boolean completed = false;
    private String status = Message.INIT;

    @Override
    public void addData(String key, String value) {
        if (Strings.isBlank(key)) {
            return;
        }
        if (Strings.isBlank(value)) {
            value = "";
        }
        int old = data == null ? 0 : data.length();
        StringBuilder sb = new StringBuilder(old + key.length() + value.length() + 16);
        if (data != null) {
            sb.append(data).append('&');
        }
        sb.append(key).append('=').append(value);
        data = sb;
    }

    @Override
    public CharSequence getData() {
        return data;
    }

    @Override
    public String getName() {
        return "MysqlMessageDefault";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean isSuccess() {
        return status.equals(Message.SUCCESS);
    }

    @Override
    public void setStatus(String tstatus) {
        if (Strings.isNotBlank(tstatus)) {
            status = tstatus;
            //调用一次就是完成
            completed = true;
        }

    }

    @Override
    public String toString() {
        return "MysqlMessageDefault{ completed=" + completed +
                ", status=" + status + " data=" + data + "}";
    }
}
