package com.lance.demo.microservice.common.util;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;

public class NetUtils {
    public static String getHostName() {
        String hostName = null;

        try {
            InetAddress address = InetAddress.getLocalHost();
            hostName = address.getHostName();
        } catch (Throwable var3) {
            ;
        }

        if (StringUtils.hasText(hostName) && !"localhost".equalsIgnoreCase(hostName)) {
            return hostName;
        } else {
            try {
                hostName = runCommand(new String[]{"hostname"});
            } catch (Exception var2) {

            }

            return StringUtils.hasText(hostName) && !"localhost".equalsIgnoreCase(hostName) ? hostName : null;
        }
    }

    public static String runCommand(String... commands) {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        Process process;
        try {
            process = processBuilder.redirectErrorStream(true).start();
        } catch (Exception e) {
            throw new RuntimeException("启动进程" + Arrays.asList(commands) + "异常", e);
        }

        StringBuilder buf = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charsets.UTF_8));
        try {
            String line;
            while ((line = in.readLine()) != null) {
                buf.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("从process中读取数据异常", e);
        } finally {
            IOUtils.closeQuietly(in);
        }

        int errorCode;
        try {
            errorCode = process.waitFor();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (errorCode == 0) {
            return buf.toString();
        } else {
            throw new RuntimeException(String.format("process执行异常.错误码[%s],异常信息:%s", errorCode, buf.toString()));
        }
    }
}
