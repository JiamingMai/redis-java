package com.kapok.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NetworkUtil {

    public static boolean isLocalHost(String host) {
        if (host.equals("127.0.0.1") || getAllLocalHostIP().contains(host)) {
            return true;
        }
        return false;
    }

    public static List<String> getAllLocalHostIP() {
        List<String> ret = new ArrayList<>();
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    for (int i = 0; i < addrs.length; i++) {
                        ret.add(addrs[i].getHostAddress());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("encountered error at getAllLocalHostIP()", ex);
        }
        return ret;
    }

}
