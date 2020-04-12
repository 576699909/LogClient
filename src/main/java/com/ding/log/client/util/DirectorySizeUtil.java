package com.ding.log.client.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.math.BigDecimal;

/**
 * 文件夹文件操作类
 */
@Slf4j
public class DirectorySizeUtil {
    /**
     * 查看文件夹大小
     *
     * @param directory
     * @return
     */
    public static Long getDirectorySize(String directory) {
        if (Strings.isBlank(directory)) {
            return null;
        }
        Long size = null;
        try {
            size = FileUtils.sizeOfDirectory(new File(directory));
        } catch (IllegalArgumentException e) {
            log.error("Directory exception :{}", e.getMessage());
        }
        return size;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1) {
            return (returnValue + "MB");
        }
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
}
