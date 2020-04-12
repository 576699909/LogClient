package com.ding.log.client.util;


import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class DirectorySizeUtilTest {

    @Test
    public void getDirectorySize() {
        String str="C:\\aaa";
        Long size = DirectorySizeUtil.getDirectorySize(str);
        if(size==null){
            log.info("未找到文件夹:{},未找到文件夹size:{} ",str,size );
            Assert.assertNull(size);
            return;
        }
        log.info("文件夹:{},size:{},{}",str,size,DirectorySizeUtil.bytes2kb(size));
    }
}
