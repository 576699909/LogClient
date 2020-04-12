package com.ding.log.client.util;

import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    /**
     * http请求的模版可以特殊设置
     * @return
     */
    public static RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
