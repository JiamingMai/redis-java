package com.kapok.util;

import java.net.InetAddress;
import java.util.List;

public class NetworkUtil {

    public static boolean isLocalHost(String host) {
        if (host.equals("127.0.0.1") || getAllLocalHostIP().contains(host)) {
            return true;
        }
        return false;
    }

    public static List<String> getAllLocalHostIP() {
        List<String> ret = null;
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
            ret = null;
        }
        return ret;
    }

}
