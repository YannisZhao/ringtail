package org.yannis.ringtail.client.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2016/7/6.
 */
public class ServiceRefHolder {
    // key: service key, value: service provider address
    private Map<String, List<String>> holder = new ConcurrentHashMap<>();
}
