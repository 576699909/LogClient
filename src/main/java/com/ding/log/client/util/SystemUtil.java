package com.ding.log.client.util;

import com.ding.log.client.message.Message;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class SystemUtil {

    /**
     * 设置系统相关的属性
     * @param message
     */
    public static void setSystemProperty(Message message) {
        Properties props = System.getProperties();
        message.addData("systemName", props.getProperty("os.name"));
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存
        String totalMemorySize = new DecimalFormat("#.##").format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
        // 剩余的物理内存
        String freePhysicalMemorySize = new DecimalFormat("#.##").format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
        // 已使用的物理内存
        String usedMemory = new DecimalFormat("#.##").format((osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024) + "G";
        message.addData("memory.total", totalMemorySize);
        message.addData("memory.free", freePhysicalMemorySize);
        message.addData("memory.used", usedMemory);
        // 磁盘使用情况
        File[] files = File.listRoots();
        for (File file : files) {
            if (file.getTotalSpace() < 1) {
                continue;
            }
            String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
            String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
            String used = new DecimalFormat("#.#").format((file.getTotalSpace() - file.getFreeSpace()) * 1.0 / 1024 / 1024 / 1024) + "G";
            String path = file.getPath();
            message.addData(path + ".disk.total", total);
            message.addData(path + ".disk.free", free);
            message.addData(path + ".disk.used", used);
        }
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
        message.addData("start.time", startTime);
        try {
            InetAddress addr = InetAddress.getLocalHost();
            message.addData("host.name", addr.getHostName());
            message.addData("ip", addr.getHostAddress());
        } catch (UnknownHostException e) {
            //Ignore
        }
    }

}
